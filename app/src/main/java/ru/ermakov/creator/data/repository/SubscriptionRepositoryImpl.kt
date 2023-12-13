package ru.ermakov.creator.data.repository

import ru.ermakov.creator.data.remote.dataSource.SubscriptionRemoteDataSource
import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.domain.model.SubscriptionRequest
import ru.ermakov.creator.domain.repository.SubscriptionRepository

class SubscriptionRepositoryImpl(
    private val subscriptionRemoteDataSource: SubscriptionRemoteDataSource
) : SubscriptionRepository {
    override suspend fun getSubscriptionsByCreatorId(creatorId: String): List<Subscription> {
        return subscriptionRemoteDataSource.getSubscriptionsByCreatorId(creatorId = creatorId)
    }

    override suspend fun getSubscriptionById(subscriptionId: Long): Subscription {
        return subscriptionRemoteDataSource.getSubscriptionById(subscriptionId = subscriptionId)
    }

    override suspend fun insertSubscription(subscriptionRequest: SubscriptionRequest) {
        subscriptionRemoteDataSource.insertSubscription(subscriptionRequest = subscriptionRequest)
    }

    override suspend fun updateSubscription(
        subscriptionId: Long,
        subscriptionRequest: SubscriptionRequest
    ) {
        subscriptionRemoteDataSource.updateSubscription(
            subscriptionId = subscriptionId,
            subscriptionRequest = subscriptionRequest
        )
    }

    override suspend fun deleteSubscriptionById(subscriptionId: Long) {
        subscriptionRemoteDataSource.deleteSubscriptionById(subscriptionId = subscriptionId)
    }
}