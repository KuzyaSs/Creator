package ru.ermakov.creator.presentation.screen.withdrawal

data class WithdrawalUiState(
    val balance: Long? = null,
    val isWithdrawn: Boolean = false,
    val isRefreshingShown: Boolean = false,
    val isProgressBarWithdrawShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)