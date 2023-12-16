package ru.ermakov.creator.presentation.screen.createSubscription

data class CreateSubscriptionUiState(
    val isSubscriptionCreated: Boolean = false,
    val isProgressBarCreateShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)