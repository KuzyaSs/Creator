package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.domain.model.UserSubscription
import ru.ermakov.creator.domain.model.UserSubscriptionRequest

interface UserSubscriptionRemoteDataSource {
    suspend fun getUserSubscriptionsByUserAndCreatorIds(
        userId: String,
        creatorId: String
    ): List<UserSubscription>

    suspend fun getSubscriberCountBySubscriptionId(subscriptionId: Long): Long
    suspend fun insertUserSubscription(userSubscriptionRequest: UserSubscriptionRequest)
    suspend fun deleteUserSubscriptionById(userId: String, userSubscriptionId: Long)
}