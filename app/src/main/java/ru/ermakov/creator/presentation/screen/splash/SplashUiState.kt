package ru.ermakov.creator.presentation.screen.splash

import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.presentation.State

data class SplashUiState(
    val signInData: SignInData? = null,
    val state: State = State.LOADING,
    val errorMessage: String = ""
)