package ru.ermakov.creator.presentation.screen.balance

import ru.ermakov.creator.domain.model.UserTransactionItem

data class BalanceUiState(
    val balance: Long = 0,
    val userTransactionItems: List<UserTransactionItem>? = null,
    val isHeaderShown: Boolean = true,
    val isRefreshingShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)