package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.mapper.toPost
import ru.ermakov.creator.data.mapper.toRemoteLikeRequest
import ru.ermakov.creator.data.mapper.toRemotePostRequest
import ru.ermakov.creator.data.remote.api.PostApi
import ru.ermakov.creator.domain.model.BlogFilter
import ru.ermakov.creator.domain.model.FeedFilter
import ru.ermakov.creator.domain.model.LikeRequest
import ru.ermakov.creator.domain.model.Post
import ru.ermakov.creator.domain.model.PostRequest

private const val LIMIT = 20L

class PostRemoteDataSourceImpl(
    private val postApi: PostApi,
    private val apiExceptionLocalizer: ApiExceptionLocalizer
) : PostRemoteDataSource {
    override suspend fun getFilteredPostPageByUserId(
        userId: String,
        feedFilter: FeedFilter,
        postId: Long,
    ): List<Post> {
        val remotePostsResponse = postApi.getFilteredPostPageByUserId(
            userId = userId,
            postType = feedFilter.postType,
            categoryIds = feedFilter.categoryIds,
            postId = postId,
            limit = LIMIT
        )
        if (remotePostsResponse.isSuccessful) {
            remotePostsResponse.body()?.let { remotePosts ->
                return remotePosts.map { remotePost ->
                    remotePost.toPost()
                }
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remotePostsResponse)
    }

    override suspend fun getFilteredFollowingPostPageByUserId(
        userId: String,
        feedFilter: FeedFilter,
        postId: Long,
    ): List<Post> {
        val remotePostsResponse = postApi.getFilteredFollowingPostPageByUserId(
            userId = userId,
            postType = feedFilter.postType,
            categoryIds = feedFilter.categoryIds,
            postId = postId,
            limit = LIMIT
        )
        if (remotePostsResponse.isSuccessful) {
            remotePostsResponse.body()?.let { remotePosts ->
                return remotePosts.map { remotePost ->
                    remotePost.toPost()
                }
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remotePostsResponse)
    }

    override suspend fun getFilteredPostPageByUserAndCreatorIds(
        userId: String,
        creatorId: String,
        blogFilter: BlogFilter,
        postId: Long,
    ): List<Post> {
        val remotePostsResponse = postApi.getFilteredPostPageByUserAndCreatorIds(
            userId = userId,
            creatorId = creatorId,
            postType = blogFilter.postType,
            tagIds = blogFilter.tagIds,
            postId = postId,
            limit = LIMIT
        )
        if (remotePostsResponse.isSuccessful) {
            remotePostsResponse.body()?.let { remotePosts ->
                return remotePosts.map { remotePost ->
                    remotePost.toPost()
                }
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remotePostsResponse)
    }

    override suspend fun getPostPageByUserIdAndSearchQuery(
        userId: String,
        searchQuery: String,
        postId: Long,
    ): List<Post> {
        val remotePostsResponse = postApi.getPostPageByUserIdAndSearchQuery(
            userId = userId,
            searchQuery = searchQuery,
            postId = postId,
            limit = LIMIT
        )
        if (remotePostsResponse.isSuccessful) {
            remotePostsResponse.body()?.let { remotePosts ->
                return remotePosts.map { remotePost ->
                    remotePost.toPost()
                }
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remotePostsResponse)
    }

    override suspend fun getPostByUserAndPostIds(userId: String, postId: Long): Post {
        val remotePostResponse = postApi.getPostByUserAndPostIds(userId = userId, postId = postId)
        if (remotePostResponse.isSuccessful) {
            remotePostResponse.body()?.let { remotePost ->
                return remotePost.toPost()
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remotePostResponse)
    }

    override suspend fun insertPost(postRequest: PostRequest) {
        val response = postApi.insertPost(remotePostRequest = postRequest.toRemotePostRequest())
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun updatePost(postId: Long, postRequest: PostRequest) {
        val response = postApi.updatePost(
            postId = postId,
            remotePostRequest = postRequest.toRemotePostRequest()
        )
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun deletePostById(postId: Long) {
        val response = postApi.deletePostById(postId = postId)
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun insertLikeToPost(likeRequest: LikeRequest) {
        val response = postApi.insertLikeToPost(
            remoteLikeRequest = likeRequest.toRemoteLikeRequest()
        )
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun deleteLikeFromPost(likeRequest: LikeRequest) {
        val response = postApi.deleteLikeFromPost(
            postId = likeRequest.postId,
            userId = likeRequest.userId
        )
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }
}