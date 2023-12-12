package ru.ermakov.creator.domain.model

import java.time.LocalDateTime

data class Follow(
    val id: Long,
    val user: User,
    val creator: Creator,
    val startDate: LocalDateTime
)
