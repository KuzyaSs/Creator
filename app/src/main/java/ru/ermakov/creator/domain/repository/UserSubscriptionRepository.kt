package ru.ermakov.creator.domain.repository

import ru.ermakov.creator.domain.model.UserSubscription
import ru.ermakov.creator.domain.model.UserSubscriptionRequest

interface UserSubscriptionRepository {
    suspend fun getUserSubscriptionsByUserAndCreatorIds(
        userId: String,
        creatorId: String
    ): List<UserSubscription>

    suspend fun getSubscriberCountBySubscriptionId(subscriptionId: Long): Long
    suspend fun insertUserSubscription(userSubscriptionRequest: UserSubscriptionRequest)
    suspend fun deleteUserSubscriptionById(userId: String, userSubscriptionId: Long)
}