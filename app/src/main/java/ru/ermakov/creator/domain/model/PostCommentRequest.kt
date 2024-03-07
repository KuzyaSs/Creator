package ru.ermakov.creator.domain.model

data class PostCommentRequest(
    val userId: String,
    val postId: Long,
    val replyCommentId: Long,
    val content: String
)