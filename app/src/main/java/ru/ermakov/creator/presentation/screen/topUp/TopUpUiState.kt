package ru.ermakov.creator.presentation.screen.topUp

data class TopUpUiState(
    val balance: Long? = null,
    val isToppedUp: Boolean = false,
    val isRefreshingShown: Boolean = false,
    val isProgressBarTopUpShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)