package ru.ermakov.creator.presentation.screen.subscriptions

import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.domain.model.UserSubscription

const val UNSELECTED_SUBSCRIPTION_ID = -1L
const val UNSELECTED_USER_SUBSCRIPTION_ID = -1L

data class SubscriptionsUiState(
    val currentUserId: String = "",
    val userSubscriptions: List<UserSubscription>? = null,
    val creatorId: String = "",
    val subscriptions: List<Subscription>? = null,
    val selectedSubscriptionId: Long = UNSELECTED_SUBSCRIPTION_ID,
    val selectedUserSubscriptionId: Long = UNSELECTED_USER_SUBSCRIPTION_ID,
    val isRefreshingShown: Boolean = false,
    val isLoading: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)
