package ru.ermakov.creator.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import ru.ermakov.creator.data.remote.model.RemoteComment
import ru.ermakov.creator.data.remote.model.RemoteCommentRequest

interface PostCommentApi {
    @GET("posts/{postId}/comments")
    suspend fun getCommentPageByPostAndReplyCommentIds(
        @Path("postId") postId: Long,
        @Query("replyCommentId") replyCommentId: Long?,
        @Query("commentId") commentId: Long,
        @Query("limit") limit: Long
    ): Response<List<RemoteComment>>

    @GET("posts/comments/{commentId}")
    suspend fun getCommentById(@Path("commentId") commentId: Long): Response<RemoteComment>

    @POST("posts/comments")
    suspend fun insertComment(@Body remoteCommentRequest: RemoteCommentRequest): Response<Unit>

    @PUT("posts/comments/{commentId}")
    suspend fun updateComment(
        @Path("commentId") commentId: Long,
        @Body remoteCommentRequest: RemoteCommentRequest
    ): Response<Unit>

    @DELETE("posts/comments/{commentId}")
    suspend fun deleteCommentById(@Path("commentId") commentId: Long): Response<Unit>
}