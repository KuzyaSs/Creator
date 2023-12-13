package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.remote.api.UserSubscriptionApi
import ru.ermakov.creator.domain.model.UserSubscription
import ru.ermakov.creator.domain.model.UserSubscriptionRequest

class UserSubscriptionRemoteDataSourceImpl(
    private val userSubscriptionApi: UserSubscriptionApi,
    private val apiExceptionLocalizer: ApiExceptionLocalizer
) : UserSubscriptionRemoteDataSource {
    override suspend fun getUserSubscriptionsByUserAndCreatorIds(
        userId: String,
        creatorId: String
    ): List<UserSubscription> {
        TODO("Not yet implemented")
    }

    override suspend fun getSubscriberCountBySubscriptionId(subscriptionId: Long): Long {
        TODO("Not yet implemented")
    }

    override suspend fun insertUserSubscription(userSubscriptionRequest: UserSubscriptionRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUserSubscriptionById(userSubscriptionId: Long) {
        TODO("Not yet implemented")
    }
}