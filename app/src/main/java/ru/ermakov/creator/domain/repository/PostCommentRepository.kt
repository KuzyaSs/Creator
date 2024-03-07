package ru.ermakov.creator.domain.repository

import ru.ermakov.creator.domain.model.PostComment
import ru.ermakov.creator.domain.model.PostCommentLikeRequest
import ru.ermakov.creator.domain.model.PostCommentRequest

interface PostCommentRepository {
    suspend fun getPostCommentPageByPostAndUserIds(
        postId: Long,
        userId: String,
        replyCommentId: Long,
        commentId: Long
    ): List<PostComment>

    suspend fun getPostCommentByCommentAndUserIds(postCommentId: Long, userId: String): PostComment

    suspend fun insertPostComment(postCommentRequest: PostCommentRequest)

    suspend fun updatePostComment(postCommentId: Long, postCommentRequest: PostCommentRequest)

    suspend fun deletePostCommentById(postCommentId: Long)
    suspend fun insertPostCommentLike(postCommentLikeRequest: PostCommentLikeRequest)
    suspend fun deletePostCommentLike(postCommentId: Long, userId: String)
}