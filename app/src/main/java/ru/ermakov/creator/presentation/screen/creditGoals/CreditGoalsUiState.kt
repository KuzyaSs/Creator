package ru.ermakov.creator.presentation.screen.creditGoals

import ru.ermakov.creator.domain.model.CreditGoal

data class CreditGoalsUiState(
    val currentUserId: String = "",
    val creditGoals: List<CreditGoal>? = null,
    val selectedCreditGoal: CreditGoal? = null,
    val isRefreshingShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)
