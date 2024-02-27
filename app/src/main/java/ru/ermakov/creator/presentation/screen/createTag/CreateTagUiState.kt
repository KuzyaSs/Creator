package ru.ermakov.creator.presentation.screen.createTag

data class CreateTagUiState(
    val isTagCreated: Boolean = false,
    val isProgressBarCreateShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)