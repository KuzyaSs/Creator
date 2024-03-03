package ru.ermakov.creator.data.remote.dataSource

import android.util.Log
import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.mapper.INVALID_COMMENT_ID
import ru.ermakov.creator.data.mapper.toComment
import ru.ermakov.creator.data.mapper.toRemoteCommentRequest
import ru.ermakov.creator.data.remote.api.PostCommentApi
import ru.ermakov.creator.domain.model.Comment
import ru.ermakov.creator.domain.model.CommentRequest

private const val LIMIT = 20L

class PostCommentRemoteDataSourceImpl(
    private val postCommentApi: PostCommentApi,
    private val apiExceptionLocalizer: ApiExceptionLocalizer
) : PostCommentRemoteDataSource {
    override suspend fun getCommentPageByPostAndReplyCommentIds(
        postId: Long,
        replyCommentId: Long,
        commentId: Long
    ): List<Comment> {
        val remoteCommentsResponse = postCommentApi.getCommentPageByPostAndReplyCommentIds(
            postId = postId,
            replyCommentId = if (replyCommentId == INVALID_COMMENT_ID) null else replyCommentId,
            commentId = commentId,
            limit = LIMIT
        )
        if (remoteCommentsResponse.isSuccessful) {
            Log.d("MY_TAG", "getCommentPageByPostAndReplyCommentIds SUCCESS ${remoteCommentsResponse.body()}")
            remoteCommentsResponse.body()?.let { remoteComments ->
                return remoteComments.map { remoteComment ->
                    remoteComment.toComment()
                }
            }
        }
        Log.d("MY_TAG", "getCommentPageByPostAndReplyCommentIds ERROR ${remoteCommentsResponse.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = remoteCommentsResponse)
    }

    override suspend fun getCommentById(commentId: Long): Comment {
        val remoteCommentResponse = postCommentApi.getCommentById(commentId = commentId)
        if (remoteCommentResponse.isSuccessful) {
            Log.d("MY_TAG", "getCommentById SUCCESS ${remoteCommentResponse.body()}")
            remoteCommentResponse.body()?.let { remoteComment ->
                return remoteComment.toComment()
            }
        }
        Log.d("MY_TAG", "getCommentById ERROR ${remoteCommentResponse.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = remoteCommentResponse)
    }

    override suspend fun insertComment(commentRequest: CommentRequest) {
        val response = postCommentApi.insertComment(
            remoteCommentRequest = commentRequest.toRemoteCommentRequest()
        )
        if (response.isSuccessful) {
            Log.d("MY_TAG", "insertComment SUCCESS ${response.body()}")
            return
        }
        Log.d("MY_TAG", "insertComment ERROR ${response.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun updateComment(commentId: Long, commentRequest: CommentRequest) {
        val response = postCommentApi.updateComment(
            commentId = commentId,
            remoteCommentRequest = commentRequest.toRemoteCommentRequest()
        )
        if (response.isSuccessful) {
            Log.d("MY_TAG", "updateComment SUCCESS ${response.body()}")
            return
        }
        Log.d("MY_TAG", "updateComment ERROR ${response.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun deleteCommentById(commentId: Long) {
        val response = postCommentApi.deleteCommentById(commentId = commentId)
        if (response.isSuccessful) {
            Log.d("MY_TAG", "deleteCommentById SUCCESS ${response.body()}")
            return
        }
        Log.d("MY_TAG", "deleteCommentById ERROR ${response.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }
}