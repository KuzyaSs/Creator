package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.domain.model.Comment
import ru.ermakov.creator.domain.model.CommentRequest

interface PostCommentRemoteDataSource {
    suspend fun getCommentPageByPostAndReplyCommentIds(
        postId: Long,
        replyCommentId: Long,
        commentId: Long
    ): List<Comment>

    suspend fun getCommentById(commentId: Long): Comment

    suspend fun insertComment(commentRequest: CommentRequest)

    suspend fun updateComment(commentId: Long, commentRequest: CommentRequest)

    suspend fun deleteCommentById(commentId: Long)
}