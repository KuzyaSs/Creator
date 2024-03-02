package ru.ermakov.creator.data.repository

import ru.ermakov.creator.data.remote.dataSource.PostRemoteDataSource
import ru.ermakov.creator.domain.model.BlogFilter
import ru.ermakov.creator.domain.model.FeedFilter
import ru.ermakov.creator.domain.model.LikeRequest
import ru.ermakov.creator.domain.model.Post
import ru.ermakov.creator.domain.model.PostRequest
import ru.ermakov.creator.domain.repository.PostRepository

class PostRepositoryImpl(private val postRemoteDataSource: PostRemoteDataSource) : PostRepository {
    override suspend fun getFilteredPostPageByUserId(
        userId: String,
        feedFilter: FeedFilter,
        postId: Long,
    ): List<Post> {
        return postRemoteDataSource.getFilteredPostPageByUserId(
            userId = userId,
            feedFilter = feedFilter,
            postId = postId
        )
    }

    override suspend fun getFilteredFollowingPostPageByUserId(
        userId: String,
        feedFilter: FeedFilter,
        postId: Long,
    ): List<Post> {
        return postRemoteDataSource.getFilteredFollowingPostPageByUserId(
            userId = userId,
            feedFilter = feedFilter,
            postId = postId
        )
    }

    override suspend fun getFilteredPostPageByUserAndCreatorIds(
        userId: String,
        creatorId: String,
        blogFilter: BlogFilter,
        postId: Long,
    ): List<Post> {
        return postRemoteDataSource.getFilteredPostPageByUserAndCreatorIds(
            userId = userId,
            creatorId = creatorId,
            blogFilter = blogFilter,
            postId = postId
        )
    }

    override suspend fun getPostPageByUserIdAndSearchQuery(
        userId: String,
        searchQuery: String,
        postId: Long,
    ): List<Post> {
        return postRemoteDataSource.getPostPageByUserIdAndSearchQuery(
            userId = userId,
            searchQuery = searchQuery,
            postId = postId
        )
    }

    override suspend fun getPostByUserAndPostIds(userId: String, postId: Long): Post {
        return postRemoteDataSource.getPostByUserAndPostIds(userId = userId, postId = postId)
    }

    override suspend fun insertPost(postRequest: PostRequest) {
        postRemoteDataSource.insertPost(postRequest = postRequest)
    }

    override suspend fun updatePost(postId: Long, postRequest: PostRequest) {
        postRemoteDataSource.updatePost(postId = postId, postRequest = postRequest)

    }

    override suspend fun deletePostById(postId: Long) {
        postRemoteDataSource.deletePostById(postId = postId)
    }

    override suspend fun insertLikeToPost(likeRequest: LikeRequest) {
        postRemoteDataSource.insertLikeToPost(likeRequest = likeRequest)
    }

    override suspend fun deleteLikeFromPost(likeRequest: LikeRequest) {
        postRemoteDataSource.deleteLikeFromPost(likeRequest = likeRequest)
    }
}