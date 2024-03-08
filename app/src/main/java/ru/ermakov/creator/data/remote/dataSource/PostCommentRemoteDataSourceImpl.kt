package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.mapper.INVALID_COMMENT_ID
import ru.ermakov.creator.data.mapper.toPostComment
import ru.ermakov.creator.data.mapper.toRemotePostCommentLikeRequest
import ru.ermakov.creator.data.mapper.toRemotePostCommentRequest
import ru.ermakov.creator.data.remote.api.PostCommentApi
import ru.ermakov.creator.domain.model.PostComment
import ru.ermakov.creator.domain.model.PostCommentLikeRequest
import ru.ermakov.creator.domain.model.PostCommentRequest

private const val LIMIT = 20L

class PostCommentRemoteDataSourceImpl(
    private val postCommentApi: PostCommentApi,
    private val apiExceptionLocalizer: ApiExceptionLocalizer
) : PostCommentRemoteDataSource {
    override suspend fun getPostCommentPageByPostAndUserIds(
        postId: Long,
        userId: String,
        replyCommentId: Long,
        postCommentId: Long
    ): List<PostComment> {
        val remotePostCommentsResponse = postCommentApi.getPostCommentPageByPostAndUserIds(
            postId = postId,
            userId = userId,
            replyCommentId = if (replyCommentId == INVALID_COMMENT_ID) null else replyCommentId,
            postCommentId = postCommentId,
            limit = LIMIT
        )
        if (remotePostCommentsResponse.isSuccessful) {
            remotePostCommentsResponse.body()?.let { remoteComments ->
                return remoteComments.map { remoteComment ->
                    remoteComment.toPostComment()
                }
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remotePostCommentsResponse)
    }

    override suspend fun getPostCommentByCommentAndUserIds(
        postCommentId: Long,
        userId: String
    ): PostComment {
        val remotePostCommentResponse = postCommentApi.getPostCommentByCommentAndUserIds(
            postCommentId = postCommentId,
            userId = userId
        )
        if (remotePostCommentResponse.isSuccessful) {
            remotePostCommentResponse.body()?.let { remoteComment ->
                return remoteComment.toPostComment()
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remotePostCommentResponse)
    }

    override suspend fun insertPostComment(postCommentRequest: PostCommentRequest): Long {
        val response = postCommentApi.insertPostComment(
            remotePostCommentRequest = postCommentRequest.toRemotePostCommentRequest()
        )
        if (response.isSuccessful) {
            response.body()?.let { postCommentId ->
                return postCommentId
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun updatePostComment(
        postCommentId: Long,
        postCommentRequest: PostCommentRequest
    ) {
        val response = postCommentApi.updatePostComment(
            postCommentId = postCommentId,
            remotePostCommentRequest = postCommentRequest.toRemotePostCommentRequest()
        )
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun deletePostCommentById(postCommentId: Long) {
        val response = postCommentApi.deletePostCommentById(postCommentId = postCommentId)
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun insertPostCommentLike(postCommentLikeRequest: PostCommentLikeRequest) {
        val response = postCommentApi.insertPostCommentLike(
            remotePostCommentLikeRequest = postCommentLikeRequest.toRemotePostCommentLikeRequest()
        )
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun deletePostCommentLike(postCommentId: Long, userId: String) {
        val response = postCommentApi.deletePostCommentLike(
            postCommentId = postCommentId,
            userId = userId
        )
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }
}