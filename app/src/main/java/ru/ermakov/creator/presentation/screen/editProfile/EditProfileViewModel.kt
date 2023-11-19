package ru.ermakov.creator.presentation.screen.editProfile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ermakov.creator.domain.useCase.common.CancelUploadTaskUseCase
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.common.UpdateUserUseCase
import ru.ermakov.creator.domain.useCase.common.UploadFileUseCase
import ru.ermakov.creator.presentation.State
import ru.ermakov.creator.presentation.exception.ExceptionHandler

class EditProfileViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val cancelUploadTaskUseCase: CancelUploadTaskUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _editProfileUiState = MutableLiveData(EditProfileUiState())
    val editProfileUiState: LiveData<EditProfileUiState> = _editProfileUiState

    init {
        setCurrentUser()
    }

    private fun setCurrentUser() {
        _editProfileUiState.value = _editProfileUiState.value?.copy(state = State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUser = getCurrentUserUseCase()
                _editProfileUiState.postValue(
                    _editProfileUiState.value?.copy(
                        currentUser = currentUser,
                        state = State.SUCCESS
                    )
                )
            } catch (exception: Exception) {
                handleException(exception = exception)
            }
        }
    }

    fun uploadFile(uri: Uri, name: String) {
        _editProfileUiState.value = _editProfileUiState.value?.copy(
            state = State.LOADING,
            isFileUploading = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                editProfileUiState.value?.currentUser?.let { currentUser ->
                    uploadFileUseCase(
                        user = currentUser,
                        uri = uri.toString(),
                        name = name
                    ).onCompletion { throwable ->
                        if (throwable != null) {
                            cancelUploadTask()
                            handleException(exception = throwable)
                        }
                    }.collect { uploadedFile ->
                        if (uploadedFile.url.isNotEmpty()) {
                            updateUserUseCase(user = currentUser.copy(profileAvatarUrl = uploadedFile.url))
                            _editProfileUiState.postValue(
                                _editProfileUiState.value?.copy(
                                    state = State.SUCCESS,
                                    isFileUploading = false
                                )
                            )
                            withContext(Dispatchers.Main) { setCurrentUser() }
                        }
                    }
                }
            } catch (exception: Exception) {
                cancelUploadTask()
                handleException(exception = exception)
            }
        }
    }

    private fun handleException(exception: Throwable) {
        val errorMessage = exceptionHandler.handleException(exception = exception)
        _editProfileUiState.postValue(
            _editProfileUiState.value?.copy(
                errorMessage = errorMessage,
                state = State.ERROR
            )
        )
    }

    fun cancelUploadTask() {
        cancelUploadTaskUseCase()
    }
}