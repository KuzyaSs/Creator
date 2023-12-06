package ru.ermakov.creator.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.ermakov.creator.data.remote.model.RemoteCreator
import ru.ermakov.creator.data.remote.model.RemoteUser
import ru.ermakov.creator.domain.model.AuthUser
import ru.ermakov.creator.domain.model.Creator

interface CreatorApi {

    @GET("creators")
    suspend fun getCreatorsByPage(searchQuery: String, page: Int): Response<List<RemoteCreator>>

    @GET("creators/{userId}")
    suspend fun getCreatorByUserId(userId: String): Response<RemoteCreator>
}