package ru.ermakov.creator.presentation.screen.passwordRecovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.useCase.passwordRecovery.PasswordRecoveryUseCase
import ru.ermakov.creator.presentation.State
import ru.ermakov.creator.presentation.exception.ExceptionHandler

class PasswordRecoveryViewModel(
    private val passwordRecoveryUseCase: PasswordRecoveryUseCase,
    private val exceptionHandler: ExceptionHandler
) :
    ViewModel() {
    private val _passwordRecoveryUiState = MutableLiveData<PasswordRecoveryUiState>()
    val passwordRecoveryUiState: LiveData<PasswordRecoveryUiState> get() = _passwordRecoveryUiState

    fun resetPassword(email: String) {
        _passwordRecoveryUiState.value = _passwordRecoveryUiState.value?.copy(state = State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                passwordRecoveryUseCase(email = email)
                _passwordRecoveryUiState.postValue(
                    _passwordRecoveryUiState.value?.copy(
                        email = email,
                        state = State.SUCCESS
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _passwordRecoveryUiState.postValue(
                    _passwordRecoveryUiState.value?.copy(
                        errorMessage = errorMessage,
                        state = State.ERROR
                    )
                )
            }
        }
    }
}