package ru.ermakov.creator.domain.model

import java.time.LocalDate

data class Follow(
    val id: Long,
    val user: User,
    val creator: User,
    val startDate: LocalDate
)
