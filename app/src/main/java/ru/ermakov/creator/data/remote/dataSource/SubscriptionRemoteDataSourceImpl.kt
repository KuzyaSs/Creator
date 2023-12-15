package ru.ermakov.creator.data.remote.dataSource

import android.util.Log
import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.mapper.toFollow
import ru.ermakov.creator.data.mapper.toRemoteSubscriptionRequest
import ru.ermakov.creator.data.mapper.toSubscription
import ru.ermakov.creator.data.remote.api.SubscriptionApi
import ru.ermakov.creator.data.remote.model.RemoteFollowRequest
import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.domain.model.SubscriptionRequest

class SubscriptionRemoteDataSourceImpl(
    private val subscriptionApi: SubscriptionApi,
    private val apiExceptionLocalizer: ApiExceptionLocalizer
) : SubscriptionRemoteDataSource {
    override suspend fun getSubscriptionsByCreatorId(creatorId: String): List<Subscription> {
        val remoteSubscriptionsResponse = subscriptionApi.getSubscriptionsByCreatorId(
            creatorId = creatorId
        )
        if (remoteSubscriptionsResponse.isSuccessful) {
            remoteSubscriptionsResponse.body()?.let { remoteSubscriptions ->
                return remoteSubscriptions.map { remoteSubscription ->
                    remoteSubscription.toSubscription()
                }
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remoteSubscriptionsResponse)
    }

    override suspend fun getSubscriptionById(subscriptionId: Long): Subscription {
        val remoteSubscriptionResponse = subscriptionApi.getSubscriptionById(
            subscriptionId = subscriptionId
        )
        if (remoteSubscriptionResponse.isSuccessful) {
            Log.d("MY_TAG", "getSubscriptionById SUCCESS ${remoteSubscriptionResponse.body()}")
            remoteSubscriptionResponse.body()?.let { remoteSubscription ->
                return remoteSubscription.toSubscription()
            }
        }
        Log.d("MY_TAG", "getSubscriptionById ERROR ${remoteSubscriptionResponse.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = remoteSubscriptionResponse)
    }

    override suspend fun insertSubscription(subscriptionRequest: SubscriptionRequest) {
        val response = subscriptionApi.insertSubscription(
            remoteSubscriptionRequest = subscriptionRequest.toRemoteSubscriptionRequest()
        )
        if (response.isSuccessful) {
            Log.d("MY_TAG", "insertSubscription SUCCESS ${response.body()}")
            return
        }
        Log.d("MY_TAG", "insertSubscription ERROR ${response.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun updateSubscription(
        subscriptionId: Long,
        subscriptionRequest: SubscriptionRequest
    ) {
        val response = subscriptionApi.updateSubscription(
            subscriptionId = subscriptionId,
            remoteSubscriptionRequest = subscriptionRequest.toRemoteSubscriptionRequest()
        )
        if (response.isSuccessful) {
            Log.d("MY_TAG", "updateSubscription SUCCESS ${response.body()}")
            return
        }
        Log.d("MY_TAG", "updateSubscription ERROR ${response.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun deleteSubscriptionById(subscriptionId: Long) {
        val response = subscriptionApi.deleteSubscriptionById(subscriptionId = subscriptionId)
        if (response.isSuccessful) {
            Log.d("MY_TAG", "deleteSubscriptionById SUCCESS ${response.body()}")
            return
        }
        Log.d("MY_TAG", "deleteSubscriptionById ERROR ${response.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }
}