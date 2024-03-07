package ru.ermakov.creator.domain.model

data class PostCommentItem(
    val id: Long,
    val user: User,
    val postId: Long,
    val replyCommentId: Long,
    val content: String,
    val publicationDate: String,
    val likeCount: Long,
    val isLiked: Boolean,
    val isEdited: Boolean
)