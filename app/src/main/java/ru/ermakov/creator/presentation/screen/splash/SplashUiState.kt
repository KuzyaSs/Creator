package ru.ermakov.creator.presentation.screen.splash

import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.presentation.State

data class SplashUiState(
    val state: State = State.INITIAL,
    val errorMessage: String = ""
)