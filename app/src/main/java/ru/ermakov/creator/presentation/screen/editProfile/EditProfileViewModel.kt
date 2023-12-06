package ru.ermakov.creator.presentation.screen.editProfile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.EditProfileImageOption
import ru.ermakov.creator.domain.useCase.shared.CancelUploadTaskUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateBioUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateUserImageUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateUsernameUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UploadProfileFileUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class EditProfileViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateUserImageUseCase: UpdateUserImageUseCase,
    private val updateUsernameUseCase: UpdateUsernameUseCase,
    private val updateBioUseCase: UpdateBioUseCase,
    private val uploadProfileFileUseCase: UploadProfileFileUseCase,
    private val cancelUploadTaskUseCase: CancelUploadTaskUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _editProfileUiState = MutableLiveData(EditProfileUiState())
    val editProfileUiState: LiveData<EditProfileUiState> = _editProfileUiState

    init {
        setCurrentUser()
    }

    fun editProfileImage(uri: Uri, fileName: String) {
        _editProfileUiState.postValue(
            _editProfileUiState.value?.copy(
                isLoadingShown = true,
                isEditProfileErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                uploadProfileFileUseCase(
                    user = _editProfileUiState.value?.currentUser,
                    uri = uri.toString(),
                    fileName = fileName,
                    editProfileImageOption = _editProfileUiState.value?.selectedEditProfileImageOption
                ).onCompletion { throwable ->
                    if (throwable != null) {
                        cancelUploadTask()
                        val errorMessage = exceptionHandler.handleException(exception = throwable)
                        _editProfileUiState.postValue(
                            _editProfileUiState.value?.copy(
                                isLoadingShown = false,
                                isEditProfileErrorMessageShown = true,
                                editProfileErrorMessage = errorMessage,
                            )
                        )
                    }
                }.collect { uploadedFile ->
                    updateUserImageUseCase(
                        uploadedFile = uploadedFile,
                        editProfileImageOption = _editProfileUiState.value?.selectedEditProfileImageOption
                    )
                    if (uploadedFile.url.isNotEmpty()) {
                        setCurrentUser()
                        _editProfileUiState.postValue(
                            _editProfileUiState.value?.copy(
                                isLoadingShown = false,
                                isEditProfileErrorMessageShown = false
                            )
                        )
                    }
                }
            } catch (exception: Exception) {
                cancelUploadTask()
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _editProfileUiState.postValue(
                    _editProfileUiState.value?.copy(
                        isLoadingShown = false,
                        isEditProfileErrorMessageShown = true,
                        editProfileErrorMessage = errorMessage,
                    )
                )
            }
        }
    }

    fun editUsername(username: String) {
        _editProfileUiState.postValue(
            _editProfileUiState.value?.copy(
                isLoadingShown = true,
                isEditUsernameErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateUsernameUseCase(username = username)
                setCurrentUser()
                _editProfileUiState.postValue(
                    _editProfileUiState.value?.copy(
                        isLoadingShown = false,
                        isEditUsernameErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _editProfileUiState.postValue(
                    _editProfileUiState.value?.copy(
                        isLoadingShown = false,
                        isEditUsernameErrorMessageShown = true,
                        editUsernameErrorMessage = errorMessage,
                    )
                )
            }
        }
    }

    fun editBio(bio: String) {
        _editProfileUiState.postValue(
            _editProfileUiState.value?.copy(
                isLoadingShown = true,
                isEditBioErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateBioUseCase(bio = bio)
                setCurrentUser()
                _editProfileUiState.postValue(
                    _editProfileUiState.value?.copy(
                        isLoadingShown = false,
                        isEditBioErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _editProfileUiState.postValue(
                    _editProfileUiState.value?.copy(
                        isLoadingShown = false,
                        isEditBioErrorMessageShown = true,
                        editBioErrorMessage = errorMessage,
                    )
                )
            }
        }
    }

    fun refreshCurrentUser() {
        _editProfileUiState.value = _editProfileUiState.value?.copy(isRefreshingShown = true)
        setCurrentUser()
    }

    fun setEditProfileOptionToProfileAvatar() {
        _editProfileUiState.value = _editProfileUiState.value?.copy(
            selectedEditProfileImageOption = EditProfileImageOption.PROFILE_AVATAR,
            isEditProfileErrorMessageShown = false
        )
    }

    fun setEditProfileOptionToProfileBackground() {
        _editProfileUiState.value = _editProfileUiState.value?.copy(
            selectedEditProfileImageOption = EditProfileImageOption.PROFILE_BACKGROUND,
            isEditProfileErrorMessageShown = false
        )
    }

    fun cancelUploadTask() {
        _editProfileUiState.postValue(
            _editProfileUiState.value?.copy(
                isLoadingShown = false,
                isEditProfileErrorMessageShown = false,
            )
        )
        cancelUploadTaskUseCase()
    }

    fun clearEditProfileErrorMessage() {
        _editProfileUiState.value = _editProfileUiState.value?.copy(
            isEditProfileErrorMessageShown = false,
            editProfileErrorMessage = ""
        )
    }

    private fun setCurrentUser() {
        _editProfileUiState.postValue(
            _editProfileUiState.value?.copy(
                isEditProfileErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUser = getCurrentUserUseCase()
                _editProfileUiState.postValue(
                    _editProfileUiState.value?.copy(
                        currentUser = currentUser,
                        isRefreshingShown = false,
                        isEditProfileErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _editProfileUiState.postValue(
                    _editProfileUiState.value?.copy(
                        isRefreshingShown = false,
                        isEditProfileErrorMessageShown = true,
                        editProfileErrorMessage = errorMessage,
                    )
                )
            }
        }
    }
}