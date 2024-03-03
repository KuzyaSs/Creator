package ru.ermakov.creator.data.repository

import ru.ermakov.creator.data.remote.dataSource.PostCommentRemoteDataSource
import ru.ermakov.creator.domain.model.Comment
import ru.ermakov.creator.domain.model.CommentRequest
import ru.ermakov.creator.domain.repository.PostCommentRepository

class PostCommentRepositoryImpl(
    private val postCommentRemoteDataSource: PostCommentRemoteDataSource
) : PostCommentRepository {
    override suspend fun getCommentPageByPostAndReplyCommentIds(
        postId: Long,
        replyCommentId: Long,
        commentId: Long
    ): List<Comment> {
        return postCommentRemoteDataSource.getCommentPageByPostAndReplyCommentIds(
            postId = postId,
            replyCommentId = replyCommentId,
            commentId = commentId
        )
    }

    override suspend fun getCommentById(commentId: Long): Comment {
        return postCommentRemoteDataSource.getCommentById(commentId = commentId)
    }

    override suspend fun insertComment(commentRequest: CommentRequest) {
        postCommentRemoteDataSource.insertComment(commentRequest = commentRequest)
    }

    override suspend fun updateComment(commentId: Long, commentRequest: CommentRequest) {
        postCommentRemoteDataSource.updateComment(
            commentId = commentId,
            commentRequest = commentRequest
        )
    }

    override suspend fun deleteCommentById(commentId: Long) {
        postCommentRemoteDataSource.deleteCommentById(commentId = commentId)
    }
}