package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.domain.model.LikeRequest
import ru.ermakov.creator.domain.model.Post
import ru.ermakov.creator.domain.model.PostRequest

interface PostRemoteDataSource {
    suspend fun getFilteredPostPageByUserId(
        userId: String,
        postType: String,
        categoryIds: List<Long>,
        postId: Long,
    ): List<Post>

    suspend fun getFilteredFollowingPostPageByUserId(
        userId: String,
        postType: String,
        categoryIds: List<Long>,
        postId: Long,
    ): List<Post>

    suspend fun getFilteredPostPageByUserAndCreatorIds(
        userId: String,
        creatorId: String,
        postType: String,
        tagIds: List<Long>,
        postId: Long,
    ): List<Post>

    suspend fun getPostPageByUserIdAndSearchQuery(
        userId: String,
        searchQuery: String,
        postId: Long,
    ): List<Post>

    suspend fun getPostByUserAndPostIds(userId: String, postId: Long): Post

    suspend fun insertPost(postRequest: PostRequest)

    suspend fun updatePost(postId: Long, postRequest: PostRequest)

    suspend fun deletePostById(postId: Long)

    suspend fun insertLikeToPost(likeRequest: LikeRequest)

    suspend fun deleteLikeFromPost(likeRequest: LikeRequest)
}