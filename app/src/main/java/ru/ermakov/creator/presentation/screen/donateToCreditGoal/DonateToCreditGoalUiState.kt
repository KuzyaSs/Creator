package ru.ermakov.creator.presentation.screen.donateToCreditGoal

import ru.ermakov.creator.domain.model.CreditGoal

data class DonateToCreditGoalUiState(
    val creditGoal: CreditGoal? = null,
    val balance: Long = 0,
    val isDonated: Boolean = false,
    val isRefreshingShown: Boolean = false,
    val isProgressBarDonateShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)