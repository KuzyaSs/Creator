package ru.ermakov.creator.presentation.screen.changePassword

data class ChangePasswordUiState(
    val isProgressBarConfirmShown: Boolean = false,
    val isChangePasswordErrorMessageShown: Boolean = false,
    val changePasswordErrorMessage: String = ""
)