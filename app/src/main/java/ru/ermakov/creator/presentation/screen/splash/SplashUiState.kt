package ru.ermakov.creator.presentation.screen.splash

import ru.ermakov.creator.presentation.util.State

data class SplashUiState(
    val state: State = State.INITIAL,
    val errorMessage: String = ""
)