package ru.ermakov.creator.presentation.screen.chooseUserCategory

import ru.ermakov.creator.domain.model.UserCategory

data class ChooseUserCategoryUiState(
    val userCategories: List<UserCategory>? = null,
    val isRefreshingShown: Boolean = false,
    val isProgressBarConfirmShown: Boolean = false,
    val isChooseCategoryErrorMessageShown: Boolean = false,
    val chooseCategoryErrorMessage: String = "",
)
