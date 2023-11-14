package ru.ermakov.creator.presentation.screen.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.domain.useCase.signUp.SignUpUseCase
import ru.ermakov.creator.presentation.State
import ru.ermakov.creator.presentation.exception.ExceptionHandler

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _signUpUiState = MutableLiveData<SignUpUiState>()
    val signUpUiState: LiveData<SignUpUiState> = _signUpUiState

    fun signUp(signUpData: SignUpData) {
        _signUpUiState.value = _signUpUiState.value?.copy(
            isNavigationAvailable = true,
            state = State.LOADING
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                signUpUseCase(signUpData = signUpData)
                _signUpUiState.postValue(
                    _signUpUiState.value?.copy(
                        signUpData = signUpData,
                        state = State.SUCCESS
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _signUpUiState.postValue(
                    _signUpUiState.value?.copy(
                        errorMessage = errorMessage,
                        state = State.ERROR
                    )
                )
            }
        }
    }

    fun resetIsNavigationAvailable() {
        _signUpUiState.value = _signUpUiState.value?.copy(isNavigationAvailable = false)
    }
}