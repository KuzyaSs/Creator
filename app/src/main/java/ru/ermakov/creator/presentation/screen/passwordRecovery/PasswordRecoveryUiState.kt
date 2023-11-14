package ru.ermakov.creator.presentation.screen.passwordRecovery

import ru.ermakov.creator.presentation.State

data class PasswordRecoveryUiState(
    val email: String = "",
    val state: State = State.LOADING,
    val errorMessage: String = ""
)