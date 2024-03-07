package ru.ermakov.creator.data.remote.dataSource

import retrofit2.http.Query
import ru.ermakov.creator.data.remote.model.RemotePostCommentLikeRequest
import ru.ermakov.creator.domain.model.PostComment
import ru.ermakov.creator.domain.model.PostCommentLikeRequest
import ru.ermakov.creator.domain.model.PostCommentRequest

interface PostCommentRemoteDataSource {
    suspend fun getPostCommentPageByPostAndUserIds(
        postId: Long,
        userId: String,
        replyCommentId: Long,
        postCommentId: Long
    ): List<PostComment>

    suspend fun getPostCommentByCommentAndUserIds(postCommentId: Long, userId: String): PostComment

    suspend fun insertPostComment(postCommentRequest: PostCommentRequest)

    suspend fun updatePostComment(postCommentId: Long, postCommentRequest: PostCommentRequest)

    suspend fun deletePostCommentById(postCommentId: Long)

    suspend fun insertPostCommentLike(postCommentLikeRequest: PostCommentLikeRequest)
    suspend fun deletePostCommentLike(postCommentId: Long, userId: String)
}