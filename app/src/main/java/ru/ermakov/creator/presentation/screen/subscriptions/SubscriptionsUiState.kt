package ru.ermakov.creator.presentation.screen.subscriptions

import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.domain.model.UserSubscription

data class SubscriptionsUiState(
    val currentUserId: String = "",
    val userSubscriptions: List<UserSubscription>? = null,
    val creatorId: String = "",
    val subscriptions: List<Subscription>? = null,
    val isRefreshingShown: Boolean = false,
    val isLoading: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)
