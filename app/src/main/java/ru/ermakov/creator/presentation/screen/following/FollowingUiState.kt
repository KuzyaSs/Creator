package ru.ermakov.creator.presentation.screen.following

import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.presentation.util.State

data class FollowingUiState(
    val currentUser: User? = null,
    val state: State = State.INITIAL,
    val errorMessage: String = ""
)