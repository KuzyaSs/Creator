package ru.ermakov.creator.presentation.screen.changePassword

data class ChangePasswordUiState(
    val isProgressBarConfirmShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)