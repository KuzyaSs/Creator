package ru.ermakov.creator.presentation.screen.purchaseSubscription

import ru.ermakov.creator.domain.model.Subscription

const val DEFAULT_SUBSCRIPTION_PERIOD = 1

data class PurchaseSubscriptionUiState(
    val subscription: Subscription? = null,
    val isSubscriptionPurchased: Boolean = false,
    val selectedSubscriptionPeriod: Int = DEFAULT_SUBSCRIPTION_PERIOD,
    val balance: Long = 0,
    val isRefreshingShown: Boolean = false,
    val isProgressBarPurchaseSubscriptionShown: Boolean = false,
    val isLoading: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)