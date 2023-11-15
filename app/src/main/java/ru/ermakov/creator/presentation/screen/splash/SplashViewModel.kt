package ru.ermakov.creator.presentation.screen.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.useCase.signIn.SignedInUseCase
import ru.ermakov.creator.presentation.State
import ru.ermakov.creator.presentation.exception.ExceptionHandler

class SplashViewModel(
    private val signedInUseCase: SignedInUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _splashUiState = MutableLiveData(SplashUiState())
    val splashUiState: LiveData<SplashUiState> = _splashUiState

    fun checkSignedInStatus() {
        _splashUiState.value = _splashUiState.value?.copy(state = State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                signedInUseCase()
                _splashUiState.postValue(_splashUiState.value?.copy(state = State.SUCCESS))
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _splashUiState.postValue(
                    _splashUiState.value?.copy(
                        errorMessage = errorMessage,
                        state = State.ERROR
                    )
                )
            }
        }
    }
}