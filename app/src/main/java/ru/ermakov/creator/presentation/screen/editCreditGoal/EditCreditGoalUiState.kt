package ru.ermakov.creator.presentation.screen.editCreditGoal

import ru.ermakov.creator.domain.model.CreditGoal

data class EditCreditGoalUiState(
    val creditGoal: CreditGoal? = null,
    val isCreditGoalEdited: Boolean = false,
    val isRefreshingShown: Boolean = false,
    val isProgressBarSaveChangesShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)