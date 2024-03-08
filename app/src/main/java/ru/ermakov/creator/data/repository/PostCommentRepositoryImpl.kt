package ru.ermakov.creator.data.repository

import ru.ermakov.creator.data.remote.dataSource.PostCommentRemoteDataSource
import ru.ermakov.creator.domain.model.PostComment
import ru.ermakov.creator.domain.model.PostCommentLikeRequest
import ru.ermakov.creator.domain.model.PostCommentRequest
import ru.ermakov.creator.domain.repository.PostCommentRepository

class PostCommentRepositoryImpl(
    private val postCommentRemoteDataSource: PostCommentRemoteDataSource
) : PostCommentRepository {
    override suspend fun getPostCommentPageByPostAndUserIds(
        postId: Long,
        userId: String,
        replyCommentId: Long,
        commentId: Long
    ): List<PostComment> {
        return postCommentRemoteDataSource.getPostCommentPageByPostAndUserIds(
            postId = postId,
            userId = userId,
            replyCommentId = replyCommentId,
            postCommentId = commentId
        )
    }

    override suspend fun getPostCommentByCommentAndUserIds(
        postCommentId: Long,
        userId: String
    ): PostComment {
        return postCommentRemoteDataSource.getPostCommentByCommentAndUserIds(
            postCommentId = postCommentId,
            userId = userId
        )
    }

    override suspend fun insertPostComment(postCommentRequest: PostCommentRequest): Long {
        return postCommentRemoteDataSource.insertPostComment(postCommentRequest = postCommentRequest)
    }

    override suspend fun updatePostComment(
        postCommentId: Long,
        postCommentRequest: PostCommentRequest
    ) {
        postCommentRemoteDataSource.updatePostComment(
            postCommentId = postCommentId,
            postCommentRequest = postCommentRequest
        )
    }

    override suspend fun deletePostCommentById(postCommentId: Long) {
        postCommentRemoteDataSource.deletePostCommentById(postCommentId = postCommentId)
    }

    override suspend fun insertPostCommentLike(postCommentLikeRequest: PostCommentLikeRequest) {
        postCommentRemoteDataSource.insertPostCommentLike(
            postCommentLikeRequest = postCommentLikeRequest
        )
    }

    override suspend fun deletePostCommentLike(postCommentId: Long, userId: String) {
        postCommentRemoteDataSource.deletePostCommentLike(
            postCommentId = postCommentId,
            userId = userId
        )
    }
}