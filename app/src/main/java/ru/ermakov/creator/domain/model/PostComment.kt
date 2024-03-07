package ru.ermakov.creator.domain.model

import java.time.LocalDateTime

class PostComment(
    val id: Long,
    val user: User,
    val postId: Long,
    val replyCommentId: Long,
    val content: String,
    val publicationDate: LocalDateTime,
    val likeCount: Long,
    val isLiked: Boolean,
    val isEdited: Boolean
)