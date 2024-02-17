package ru.ermakov.creator.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.ermakov.creator.domain.model.Category

interface CategoryApi {
    @GET("categories")
    suspend fun getAllCategories(): Response<List<Category>>

    @GET("users/{userId}/categories")
    suspend fun getCategoriesByUserId(@Path("userId") userId: String): Response<List<Category>>

    @PUT("users/{userId}/categories")
    suspend fun updateCategories(
        @Path("userId") userId: String,
        @Body categories: List<Category>
    ): Response<Unit>
}