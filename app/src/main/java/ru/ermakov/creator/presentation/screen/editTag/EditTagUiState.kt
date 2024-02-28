package ru.ermakov.creator.presentation.screen.editTag

import ru.ermakov.creator.domain.model.Tag


data class EditTagUiState(
    val tag: Tag? = null,
    val isTagEdited: Boolean = false,
    val isRefreshingShown: Boolean = false,
    val isProgressBarSaveChangesShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)
