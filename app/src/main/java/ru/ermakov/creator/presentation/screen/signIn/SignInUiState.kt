package ru.ermakov.creator.presentation.screen.signIn

import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.presentation.State

data class SignInUiState(
    val signInData: SignInData? = null,
    val state: State = State.LOADING,
    val errorMessage: String = ""
)