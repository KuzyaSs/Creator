package ru.ermakov.creator.presentation.screen.signUp

import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.presentation.model.State

data class SignUpUiState(
    val signUpData: SignUpData? = null,
    val state: State = State.INITIAL,
    val errorMessage: String = ""
)