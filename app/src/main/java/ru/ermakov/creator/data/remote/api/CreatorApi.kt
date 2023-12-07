package ru.ermakov.creator.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import ru.ermakov.creator.data.remote.model.RemoteCreator
import ru.ermakov.creator.data.remote.model.RemoteUser
import ru.ermakov.creator.domain.model.AuthUser
import ru.ermakov.creator.domain.model.Creator

interface CreatorApi {

    @GET("creators")
    suspend fun getCreatorsByPage(
        @Query("searchQuery") searchQuery: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<List<RemoteCreator>>

    @GET("creators/{userId}")
    suspend fun getCreatorByUserId(@Path("userId") userId: String): Response<RemoteCreator>
}