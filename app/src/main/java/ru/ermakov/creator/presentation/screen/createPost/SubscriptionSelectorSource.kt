package ru.ermakov.creator.presentation.screen.createPost

import ru.ermakov.creator.domain.model.Subscription

interface SubscriptionSelectorSource {
    fun getSubscriptions(): List<Subscription>?

    fun getRequiredSubscriptionIds(): List<Long>

    fun changeSelectedSubscriptionIds(selectedSubscriptionIds: List<Long>)

    fun navigateToSubscriptionsFragment()
}