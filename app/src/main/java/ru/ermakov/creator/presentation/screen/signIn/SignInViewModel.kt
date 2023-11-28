package ru.ermakov.creator.presentation.screen.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.domain.useCase.signIn.SignInUseCase
import ru.ermakov.creator.presentation.util.State
import ru.ermakov.creator.presentation.util.ExceptionHandler

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _signInUiState = MutableLiveData(SignInUiState())
    val signInUiState: LiveData<SignInUiState> get() = _signInUiState

    fun signIn(signInData: SignInData) {
        _signInUiState.value = _signInUiState.value?.copy(state = State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                signInUseCase(signInData = signInData)
                _signInUiState.postValue(
                    _signInUiState.value?.copy(
                        signInData = signInData,
                        state = State.SUCCESS
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _signInUiState.postValue(
                    _signInUiState.value?.copy(
                        errorMessage = errorMessage,
                        state = State.ERROR
                    )
                )
            }
        }
    }
}