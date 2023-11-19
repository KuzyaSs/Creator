package ru.ermakov.creator.presentation.screen.editProfile

import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.presentation.State

data class EditProfileUiState(
    val currentUser: User? = null,
    val isFileUploading: Boolean = false,
    val state: State = State.INITIAL,
    val errorMessage: String = ""
)