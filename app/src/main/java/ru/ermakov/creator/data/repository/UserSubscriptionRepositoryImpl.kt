package ru.ermakov.creator.data.repository

import ru.ermakov.creator.domain.model.UserSubscription
import ru.ermakov.creator.domain.model.UserSubscriptionRequest
import ru.ermakov.creator.domain.repository.UserSubscriptionRepository
import ru.ermakov.creator.data.remote.dataSource.UserSubscriptionRemoteDataSource

class UserSubscriptionRepositoryImpl(
    private val userSubscriptionRemoteDataSource: UserSubscriptionRemoteDataSource
) : UserSubscriptionRepository {
    override suspend fun getUserSubscriptionsByUserAndCreatorIds(
        userId: String,
        creatorId: String
    ): List<UserSubscription> {
        return userSubscriptionRemoteDataSource.getUserSubscriptionsByUserAndCreatorIds(
            userId = userId,
            creatorId = creatorId
        )
    }

    override suspend fun getSubscriberCountBySubscriptionId(subscriptionId: Long): Long {
        return userSubscriptionRemoteDataSource.getSubscriberCountBySubscriptionId(subscriptionId = subscriptionId)
    }

    override suspend fun insertUserSubscription(userSubscriptionRequest: UserSubscriptionRequest) {
        userSubscriptionRemoteDataSource.insertUserSubscription(userSubscriptionRequest = userSubscriptionRequest)
    }

    override suspend fun deleteUserSubscriptionById(userId: String, userSubscriptionId: Long) {
        userSubscriptionRemoteDataSource.deleteUserSubscriptionById(
            userId = userId,
            userSubscriptionId = userSubscriptionId
        )
    }
}