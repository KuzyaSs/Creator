package ru.ermakov.creator.domain.model

data class PostCommentLikeRequest(
    val postCommentId: Long,
    val userId: String
)