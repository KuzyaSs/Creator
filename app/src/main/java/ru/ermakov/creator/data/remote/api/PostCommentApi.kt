package ru.ermakov.creator.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import ru.ermakov.creator.data.remote.model.RemotePostComment
import ru.ermakov.creator.data.remote.model.RemotePostCommentLikeRequest
import ru.ermakov.creator.data.remote.model.RemotePostCommentRequest

interface PostCommentApi {
    @GET("posts/{postId}/comments")
    suspend fun getPostCommentPageByPostAndUserIds(
        @Path("postId") postId: Long,
        @Query("userId") userId: String,
        @Query("replyCommentId") replyCommentId: Long?,
        @Query("postCommentId") postCommentId: Long,
        @Query("limit") limit: Long
    ): Response<List<RemotePostComment>>

    @GET("posts/comments/{postCommentId}")
    suspend fun getPostCommentByCommentAndUserIds(
        @Path("postCommentId") postCommentId: Long,
        @Query("userId") userId: String
    ): Response<RemotePostComment>

    @POST("posts/comments")
    suspend fun insertPostComment(@Body remotePostCommentRequest: RemotePostCommentRequest): Response<Unit>

    @PUT("posts/comments/{postCommentId}")
    suspend fun updatePostComment(
        @Path("postCommentId") postCommentId: Long,
        @Body remotePostCommentRequest: RemotePostCommentRequest
    ): Response<Unit>

    @DELETE("posts/comments/{postCommentId}")
    suspend fun deletePostCommentById(@Path("postCommentId") postCommentId: Long): Response<Unit>

    @POST("posts/comments/likes")
    suspend fun insertPostCommentLike(
        @Body remotePostCommentLikeRequest: RemotePostCommentLikeRequest
    ): Response<Unit>

    @DELETE("posts/comments/{postCommentId}/likes")
    suspend fun deletePostCommentLike(
        @Path("postCommentId") postCommentId: Long,
        @Query("userId") userId: String
    ): Response<Unit>
}