package ru.ermakov.creator.domain.model

data class PostRequest(
    val creatorId: String,
    val title: String,
    val content: String,
    val imageUrls: List<String>,
    val tagIds: List<Long>,
    val requiredSubscriptionIds: List<Long>,
)
