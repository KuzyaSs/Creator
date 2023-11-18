package ru.ermakov.creator.presentation.screen.editProfile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.domain.useCase.account.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.editProfile.EditProfileAvatarUseCase
import ru.ermakov.creator.presentation.State
import ru.ermakov.creator.presentation.exception.ExceptionHandler
import java.time.LocalDate

class EditProfileViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val editProfileAvatarUseCase: EditProfileAvatarUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _editProfileUiState = MutableLiveData(EditProfileUiState())
    val editProfileUiState: LiveData<EditProfileUiState> = _editProfileUiState

    init {
        setCurrentUser()
    }

    fun setCurrentUser() {
        _editProfileUiState.value = _editProfileUiState.value?.copy(state = State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(2000) // remove
                // temporarily
                val currentUser = User(
                    "12",
                    "Kuzya",
                    "skepy@,ail.ru",
                    "about",
                    "https://firebasestorage.googleapis.com/v0/b/creator-26c44.appspot.com/o/myAvatar.png?alt=media&token=e564220f-2ed0-4016-92fd-52f1253072f9",
                    "",
                    LocalDate.now()
                )   // getCurrentUserUseCase()
                _editProfileUiState.postValue(
                    _editProfileUiState.value?.copy(
                        currentUser = currentUser,
                        state = State.SUCCESS
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _editProfileUiState.postValue(
                    _editProfileUiState.value?.copy(
                        errorMessage = errorMessage,
                        state = State.ERROR
                    )
                )
            }
        }
    }

    fun uploadProfileAvatar(uri: Uri, name: String) {
        _editProfileUiState.value = _editProfileUiState.value?.copy(state = State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                editProfileUiState.value?.currentUser?.let { currentUser ->
                    editProfileAvatarUseCase(user = currentUser, uri = uri.toString(), name = name)
                    withContext(Dispatchers.Main) { setCurrentUser() }
                }
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _editProfileUiState.postValue(
                    _editProfileUiState.value?.copy(
                        errorMessage = errorMessage,
                        state = State.ERROR
                    )
                )
            }
        }
    }
}