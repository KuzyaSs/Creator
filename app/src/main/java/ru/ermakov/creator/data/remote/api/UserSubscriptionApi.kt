package ru.ermakov.creator.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.ermakov.creator.data.remote.model.RemoteUserSubscription
import ru.ermakov.creator.data.remote.model.RemoteUserSubscriptionRequest

interface UserSubscriptionApi {
    @GET("users/{userId}/subscriptions")
    suspend fun getUserSubscriptionsByUserAndCreatorIds(
        @Path("userId") userId: String,
        @Query("creatorId") creatorId: String
    ): Response<List<RemoteUserSubscription>>

    @GET("subscriptions/{subscriptionId}/count")
    suspend fun getSubscriberCountBySubscriptionId(
        @Path("subscriptionId") subscriptionId: Long
    ): Response<Long>

    @POST("users/{userId}/subscriptions")
    suspend fun insertUserSubscription(
        @Path("userId") userId: String,
        @Body remoteUserSubscriptionRequest: RemoteUserSubscriptionRequest
    ): Response<Unit>

    @DELETE("users/{userId}/subscriptions/{subscriptionId}")
    suspend fun deleteUserSubscriptionById(
        @Path("userId") userId: String,
        @Path("subscriptionId") userSubscriptionId: Long
    ): Response<Unit>
}