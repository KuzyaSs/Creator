package ru.ermakov.creator.presentation.screen.changePassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.useCase.changePassword.ChangePasswordUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class ChangePasswordViewModel(
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val exceptionHandler: ExceptionHandler
) :
    ViewModel() {
    private val _changePasswordUiState = MutableLiveData(ChangePasswordUiState())
    val changePasswordUiState: LiveData<ChangePasswordUiState> = _changePasswordUiState

    fun confirmNewPassword(
        currentPassword: String,
        newPassword: String,
        confirmationNewPassword: String
    ) {
        _changePasswordUiState.postValue(
            _changePasswordUiState.value?.copy(
                isProgressBarConfirmShown = true,
                isChangePasswordErrorMessageShown = false,
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                changePasswordUseCase(
                    currentPassword = currentPassword,
                    newPassword = newPassword,
                    confirmationNewPassword = confirmationNewPassword
                )
                _changePasswordUiState.postValue(
                    _changePasswordUiState.value?.copy(
                        isProgressBarConfirmShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _changePasswordUiState.postValue(
                    _changePasswordUiState.value?.copy(
                        isProgressBarConfirmShown = false,
                        isChangePasswordErrorMessageShown = true,
                        changePasswordErrorMessage = errorMessage
                    )
                )
            }
        }
    }
}