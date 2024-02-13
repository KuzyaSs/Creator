package ru.ermakov.creator.domain.model

data class LikeRequest(
    val userId: String,
    val postId: Long,
)
