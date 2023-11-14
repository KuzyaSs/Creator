package ru.ermakov.creator.presentation.screen.editProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.domain.useCase.account.GetCurrentUserUseCase
import ru.ermakov.creator.presentation.State
import ru.ermakov.creator.presentation.exception.ExceptionHandler

class EditProfileViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _editProfileUiState = MutableLiveData<EditProfileUiState>()
    val editProfileUiState: LiveData<EditProfileUiState> get() = _editProfileUiState

    fun setUser() {
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

    fun editUsername() {
        // editProfileUseCase.execute(user)
    }
}