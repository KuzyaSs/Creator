package ru.ermakov.creator.presentation.screen.chooseCategory

import ru.ermakov.creator.domain.model.Category

data class ChooseCategoryUiState(
    val categories: List<Category>? = null,
    val isRefreshingShown: Boolean = false,
    val isProgressBarConfirmShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)
