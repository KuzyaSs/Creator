package ru.ermakov.creator.domain.repository

import ru.ermakov.creator.domain.model.BlogFilter
import ru.ermakov.creator.domain.model.FeedFilter
import ru.ermakov.creator.domain.model.LikeRequest
import ru.ermakov.creator.domain.model.Post
import ru.ermakov.creator.domain.model.PostRequest

interface PostRepository {
    // In the discover screen.
    suspend fun getFilteredPostPageByUserId(
        userId: String,
        feedFilter: FeedFilter,
        postId: Long,
    ): List<Post>

    // In the following screen.
    suspend fun getFilteredFollowingPostPageByUserId(
        userId: String,
        feedFilter: FeedFilter,
        postId: Long,
    ): List<Post>

    // In the blog screen.
    suspend fun getFilteredPostPageByUserAndCreatorIds(
        userId: String,
        creatorId: String,
        blogFilter: BlogFilter,
        postId: Long,
    ): List<Post>

    // In the search screen.
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