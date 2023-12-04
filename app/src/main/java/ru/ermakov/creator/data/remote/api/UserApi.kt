package ru.ermakov.creator.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.ermakov.creator.data.remote.model.RemoteUser
import ru.ermakov.creator.domain.model.AuthUser

interface UserApi {
    @GET("users/{userId}")
    suspend fun getUserById(@Path("userId") userId: String): Response<RemoteUser>

    @POST("users")
    suspend fun insertUser(@Body authUser: AuthUser): Response<Unit>

    @PUT("users")
    suspend fun updateUser(@Body user: RemoteUser): Response<Unit>
}