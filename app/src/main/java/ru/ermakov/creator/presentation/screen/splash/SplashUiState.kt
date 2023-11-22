package ru.ermakov.creator.presentation.screen.splash

import ru.ermakov.creator.presentation.model.State

data class SplashUiState(
    val state: State = State.INITIAL,
    val errorMessage: String = ""
)