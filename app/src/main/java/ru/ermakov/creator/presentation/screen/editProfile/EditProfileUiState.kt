package ru.ermakov.creator.presentation.screen.editProfile

import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.presentation.State

data class EditProfileUiState(
    val currentUser: User? = null,
    val state: State = State.LOADING,
    val errorMessage: String = ""
)