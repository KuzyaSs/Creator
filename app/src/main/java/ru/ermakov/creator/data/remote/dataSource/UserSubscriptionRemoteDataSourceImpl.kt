package ru.ermakov.creator.data.remote.dataSource

import android.util.Log
import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.mapper.toRemoteSubscriptionRequest
import ru.ermakov.creator.data.mapper.toRemoteUserSubscriptionRequest
import ru.ermakov.creator.data.mapper.toSubscription
import ru.ermakov.creator.data.mapper.toUserSubscription
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
        val remoteUserSubscriptionsResponse =
            userSubscriptionApi.getUserSubscriptionsByUserAndCreatorIds(
                userId = userId,
                creatorId = creatorId
            )
        if (remoteUserSubscriptionsResponse.isSuccessful) {
            remoteUserSubscriptionsResponse.body()?.let { remoteUserSubscriptions ->
                return remoteUserSubscriptions.map { remoteUserSubscription ->
                    remoteUserSubscription.toUserSubscription()
                }
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remoteUserSubscriptionsResponse)
    }

    override suspend fun getSubscriberCountBySubscriptionId(subscriptionId: Long): Long {
        val subscriberCountResponse = userSubscriptionApi.getSubscriberCountBySubscriptionId(
            subscriptionId = subscriptionId
        )
        if (subscriberCountResponse.isSuccessful) {
            Log.d("MY_TAG", "getSubscriberCountBySubscriptionId SUCCESS ${subscriberCountResponse.body()}")
            subscriberCountResponse.body()?.let { subscriberCount ->
                return subscriberCount
            }
        }
        Log.d("MY_TAG", "getSubscriberCountBySubscriptionId ERROR ${subscriberCountResponse.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = subscriberCountResponse)
    }

    override suspend fun insertUserSubscription(userSubscriptionRequest: UserSubscriptionRequest) {
        val response = userSubscriptionApi.insertUserSubscription(
            userId = userSubscriptionRequest.userId,
            remoteUserSubscriptionRequest = userSubscriptionRequest.toRemoteUserSubscriptionRequest()
        )
        if (response.isSuccessful) {
            Log.d("MY_TAG", "insertUserSubscription SUCCESS ${response.body()}")
            return
        }
        Log.d("MY_TAG", "insertUserSubscription ERROR ${response.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun deleteUserSubscriptionById(userId: String, userSubscriptionId: Long) {
        val response = userSubscriptionApi.deleteUserSubscriptionById(
            userId = userId,
            userSubscriptionId = userSubscriptionId
        )
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }
}