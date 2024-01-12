package ru.ermakov.creator.presentation.screen.tip

data class TipUiState(
    val balance: Long? = null,
    val isTipSent: Boolean = false,
    val isRefreshingShown: Boolean = false,
    val isProgressBarSendTipShown: Boolean = false,
    val isLoading: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)