package ru.ermakov.creator.presentation.screen.editSubscription

import ru.ermakov.creator.domain.model.Subscription

data class EditSubscriptionUiState(
    val subscription: Subscription? = null,
    val isSubscriptionEdited: Boolean = false,
    val isRefreshingShown: Boolean = false,
    val isProgressBarSaveChangesShown: Boolean = false,
    val isLoading: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)