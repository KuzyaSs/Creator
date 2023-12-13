package ru.ermakov.creator.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.ermakov.creator.data.remote.model.RemoteSubscription
import ru.ermakov.creator.data.remote.model.RemoteSubscriptionRequest

interface SubscriptionApi {

    @GET("creators/{creatorId}/subscriptions")
    suspend fun getSubscriptionsByCreatorId(
        @Path("creatorId") creatorId: String
    ): Response<List<RemoteSubscription>>

    @GET("subscriptions/{subscriptionId}")
    suspend fun getSubscriptionById(
        @Path("subscriptionId") subscriptionId: Long
    ): Response<RemoteSubscription>

    @POST("subscriptions")
    suspend fun insertSubscription(
        @Body remoteSubscriptionRequest: RemoteSubscriptionRequest
    ): Response<Unit>

    @PUT("subscriptions/{subscriptionId}")
    suspend fun updateSubscription(
        @Path("subscriptionId") subscriptionId: Long,
        @Body remoteSubscriptionRequest: RemoteSubscriptionRequest
    ): Response<Unit>

    @DELETE("subscriptions/{subscriptionId}")
    suspend fun deleteSubscriptionById(@Path("subscriptionId") subscriptionId: Long): Response<Unit>
}