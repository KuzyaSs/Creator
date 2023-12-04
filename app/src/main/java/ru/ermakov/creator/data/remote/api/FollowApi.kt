package ru.ermakov.creator.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import ru.ermakov.creator.data.remote.model.RemoteFollow
import ru.ermakov.creator.data.remote.model.RemoteFollowRequest

interface FollowApi {
    @GET("users/{userId}/follows")
    suspend fun getFollowsByUserId(@Path("userId") userId: String): Response<List<RemoteFollow>>

    @HTTP(method = "DELETE", path = "follows", hasBody = true)
    suspend fun getFollowByUserAndCreatorIds(@Body remoteFollowRequest: RemoteFollowRequest): Response<RemoteFollow>

    @GET("users/{userId}/followers/count")
    suspend fun getFollowerCountByUserId(@Path("userId") userId: String): Response<Long>

    @POST("follows")
    suspend fun insertFollow(@Body remoteFollowRequest: RemoteFollowRequest): Response<Unit>

    @DELETE("follows/{followId}")
    suspend fun deleteFollowById(@Path("followId") followId: Long): Response<Unit>
}