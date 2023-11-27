package ru.ermakov.creator.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.ermakov.creator.domain.model.UserCategory

interface CategoryApi {
    @GET("users/{userId}/categories")
    suspend fun getUserCategoriesByUserId(@Path("userId") userId: String): Response<List<UserCategory>>

    @PUT("users/{userId}/categories")
    suspend fun updateUserCategories(
        @Path("userId") userId: String,
        @Body userCategories: List<UserCategory>
    ): Response<Void>
}