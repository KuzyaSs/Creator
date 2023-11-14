package ru.ermakov.creator.presentation.screen.signUp

import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.presentation.State

data class SignUpUiState(
    val signUpData: SignUpData? = null,
    val isNavigationAvailable: Boolean = false,
    val state: State = State.LOADING,
    val errorMessage: String = ""
)