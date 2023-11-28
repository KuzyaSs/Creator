package ru.ermakov.creator.presentation.screen.signIn

import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.presentation.util.State

data class SignInUiState(
    val signInData: SignInData? = null,
    val state: State = State.INITIAL,
    val errorMessage: String = ""
)