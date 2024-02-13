package ru.ermakov.creator.domain.model

import java.time.LocalDateTime

data class Post(
    val id: Long,
    val creator: Creator,
    val title: String,
    val content: String,
    val images: List<Image>,
    val tags: List<Tag>,
    val requiredSubscriptions: List<Subscription>,
    val likeCount: Long,
    val commentCount: Long,
    val publicationDate: LocalDateTime,
    val isAvailable: Boolean,
    val isLiked: Boolean,
    val isEdited: Boolean,
)
