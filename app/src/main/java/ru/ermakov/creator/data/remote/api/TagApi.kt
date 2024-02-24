package ru.ermakov.creator.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.ermakov.creator.data.remote.model.RemoteTag
import ru.ermakov.creator.data.remote.model.RemoteTagRequest

interface TagApi {
    @GET("creators/{creatorId}/tags")
    suspend fun getTagsByCreatorId(@Path("creatorId") creatorId: String): Response<List<RemoteTag>>

    @GET("tags/{tagId}")
    suspend fun getTagById(@Path("tagId") tagId: Long): Response<RemoteTag>

    @POST("tags")
    suspend fun insertTag(@Body remoteTagRequest: RemoteTagRequest): Response<Unit>

    @PUT("tags/{tagId}")
    suspend fun updateTag(
        @Path("tagId") tagId: Long,
        @Body remoteTagRequest: RemoteTagRequest
    ): Response<Unit>

    @DELETE("tags/{tagId}")
    suspend fun deleteTagById(@Path("tagId") tagId: Long): Response<Unit>
}