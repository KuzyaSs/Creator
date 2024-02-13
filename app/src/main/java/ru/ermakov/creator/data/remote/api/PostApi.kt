package ru.ermakov.creator.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import ru.ermakov.creator.data.remote.model.RemoteLikeRequest
import ru.ermakov.creator.data.remote.model.RemotePost
import ru.ermakov.creator.data.remote.model.RemotePostRequest

interface PostApi {
    @GET("posts/discover")
    suspend fun getFilteredPostPageByUserId(
        @Query("userId") userId: String,
        @Query("postType") postType: String,
        @Query("categoryIds") categoryIds: List<Long>,
        @Query("postId") postId: Long,
        @Query("limit") limit: Long
    ): Response<List<RemotePost>>

    @GET("posts/following")
    suspend fun getFilteredFollowingPostPageByUserId(
        @Query("userId") userId: String,
        @Query("postType") postType: String,
        @Query("categoryIds") categoryIds: List<Long>,
        @Query("postId") postId: Long,
        @Query("limit") limit: Long
    ): Response<List<RemotePost>>

    @GET("posts/creators/{creatorId}")
    suspend fun getFilteredPostPageByUserAndCreatorIds(
        @Query("userId") userId: String,
        @Path("creatorId") creatorId: String,
        @Query("postType") postType: String,
        @Query("tagIds") tagIds: List<Long>,
        @Query("postId") postId: Long,
        @Query("limit") limit: Long
    ): Response<List<RemotePost>>

    @GET("posts/search")
    suspend fun getPostPageByUserIdAndSearchQuery(
        @Query("userId") userId: String,
        @Query("searchQuery") searchQuery: String,
        @Query("postId") postId: Long,
        @Query("limit") limit: Long
    ): Response<List<RemotePost>>

    @GET("posts/{postId}")
    suspend fun getPostByUserAndPostIds(
        @Query("userId") userId: String,
        @Path("postId") postId: Long
    ): Response<RemotePost>

    @POST("posts")
    suspend fun insertPost(@Body remotePostRequest: RemotePostRequest): Response<Unit>

    @PUT("posts/{postId}")
    suspend fun updatePost(
        @Path("postId") postId: Long,
        @Body remotePostRequest: RemotePostRequest
    ): Response<Unit>

    @DELETE("posts/{postId}")
    suspend fun deletePostById(@Path("postId") postId: Long): Response<Unit>

    @POST("posts/likes")
    suspend fun insertLikeToPost(@Body remoteLikeRequest: RemoteLikeRequest): Response<Unit>

    @DELETE("posts/likes")
    suspend fun deleteLikeFromPost(@Body remoteLikeRequest: RemoteLikeRequest): Response<Unit>
}