package ru.ermakov.creator.domain.model

data class PostItem(
    val id: Long,
    val creator: Creator,
    val title: String,
    val content: String,
    val images: List<Image>,
    val tags: List<Tag>,
    val requiredSubscriptions: List<Subscription>,
    val likeCount: Long,
    val commentCount: Long,
    val publicationDate: String,
    val isAvailable: Boolean,
    val isLiked: Boolean,
    val isEdited: Boolean,
)
