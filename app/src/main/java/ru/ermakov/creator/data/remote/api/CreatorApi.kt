package ru.ermakov.creator.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.ermakov.creator.data.remote.model.RemoteCreator

interface CreatorApi {

    @GET("creators")
    suspend fun getCreatorPageBySearchQuery(
        @Query("searchQuery") searchQuery: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<List<RemoteCreator>>

    @GET("creators/{userId}")
    suspend fun getCreatorByUserId(@Path("userId") userId: String): Response<RemoteCreator>
}