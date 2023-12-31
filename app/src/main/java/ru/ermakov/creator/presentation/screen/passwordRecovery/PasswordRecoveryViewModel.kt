package ru.ermakov.creator.presentation.screen.passwordRecovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.useCase.passwordRecovery.RecoverPasswordByEmailUseCase
import ru.ermakov.creator.presentation.util.State
import ru.ermakov.creator.presentation.util.ExceptionHandler

class PasswordRecoveryViewModel(
    private val recoverPasswordByEmailUseCase: RecoverPasswordByEmailUseCase,
    private val exceptionHandler: ExceptionHandler
) :
    ViewModel() {
    private val _passwordRecoveryUiState = MutableLiveData(PasswordRecoveryUiState())
    val passwordRecoveryUiState: LiveData<PasswordRecoveryUiState> = _passwordRecoveryUiState

    fun resetPassword(email: String) {
        _passwordRecoveryUiState.value = _passwordRecoveryUiState.value?.copy(state = State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                recoverPasswordByEmailUseCase(email = email)
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