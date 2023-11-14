package ru.ermakov.creator.presentation.screen.account

import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.presentation.State

data class AccountUiState(
    val currentUser: User? = null,
    val state: State = State.LOADING,
    val errorMessage: String = ""
)