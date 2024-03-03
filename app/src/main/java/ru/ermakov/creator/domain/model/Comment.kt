package ru.ermakov.creator.domain.model

import java.time.LocalDateTime

class Comment(
    val id: Long,
    val user: User,
    val postId: Long,
    val replyCommentId: Long,
    val content: String,
    val publicationDate: LocalDateTime,
    val isEdited: Boolean
)