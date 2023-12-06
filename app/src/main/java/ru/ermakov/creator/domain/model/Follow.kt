package ru.ermakov.creator.domain.model

import java.time.LocalDate

data class Follow(
    val id: Long,
    val user: User,
    val creator: Creator,
    val startDate: LocalDate
)
