package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.remote.api.PostApi
import ru.ermakov.creator.domain.model.LikeRequest
import ru.ermakov.creator.domain.model.Post
import ru.ermakov.creator.domain.model.PostRequest

private const val LIMIT = 20;

class PostRemoteDataSourceImpl(
    private val postApi: PostApi,
    private val apiExceptionLocalizer: ApiExceptionLocalizer
) : PostRemoteDataSource {
    override suspend fun getFilteredPostPageByUserId(
        userId: String,
        postType: String,
        categoryIds: List<Long>,
        postId: Long,
    ): List<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun getFilteredFollowingPostPageByUserId(
        userId: String,
        postType: String,
        categoryIds: List<Long>,
        postId: Long,
    ): List<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun getFilteredPostPageByUserAndCreatorIds(
        userId: String,
        creatorId: String,
        postType: String,
        tagIds: List<Long>,
        postId: Long,
    ): List<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostPageByUserIdAndSearchQuery(
        userId: String,
        searchQuery: String,
        postId: Long,
    ): List<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostByUserAndPostIds(userId: String, postId: Long): Post {
        TODO("Not yet implemented")
    }

    override suspend fun insertPost(postRequest: PostRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun updatePost(postId: Long, postRequest: PostRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePostById(postId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun insertLikeToPost(likeRequest: LikeRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLikeFromPost(likeRequest: LikeRequest) {
        TODO("Not yet implemented")
    }
}