package ru.ermakov.creator.domain.model

data class CommentItem(
    val id: Long,
    val user: User,
    val postId: Long,
    val replyCommentId: Long,
    val content: String,
    val publicationDate: String,
    val isEdited: Boolean
)