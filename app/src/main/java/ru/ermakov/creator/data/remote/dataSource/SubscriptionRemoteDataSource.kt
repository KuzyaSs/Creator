package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.domain.model.SubscriptionRequest

interface SubscriptionRemoteDataSource {
    suspend fun getSubscriptionsByCreatorId(creatorId: String): List<Subscription>
    suspend fun getSubscriptionById(subscriptionId: Long): Subscription
    suspend fun insertSubscription(subscriptionRequest: SubscriptionRequest)
    suspend fun updateSubscription(subscriptionId: Long, subscriptionRequest: SubscriptionRequest)
    suspend fun deleteSubscriptionById(subscriptionId: Long)
}