package ru.ermakov.creator.presentation.screen.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.useCase.account.GetCurrentUserUseCase
import ru.ermakov.creator.presentation.State
import ru.ermakov.creator.presentation.exception.ExceptionHandler

class AccountViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _accountUiState = MutableLiveData(AccountUiState())
    val accountUiState: LiveData<AccountUiState> = _accountUiState

    fun setUser() {
        _accountUiState.value = _accountUiState.value?.copy(state = State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUser = getCurrentUserUseCase()
                _accountUiState.postValue(
                    _accountUiState.value?.copy(
                        currentUser = currentUser,
                        state = State.SUCCESS
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _accountUiState.postValue(
                    _accountUiState.value?.copy(
                        errorMessage = errorMessage,
                        state = State.ERROR
                    )
                )
            }
        }
    }
}