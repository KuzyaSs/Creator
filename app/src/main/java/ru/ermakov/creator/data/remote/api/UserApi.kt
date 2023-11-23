package ru.ermakov.creator.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.ermakov.creator.data.remote.model.RemoteUser

interface UserApi {
    @GET("user/{userId}")
    suspend fun getUserById(@Path("userId") userId: String): Response<RemoteUser>

    @PUT("user")
    suspend fun updateUser(@Body user: RemoteUser): Response<Void>
}