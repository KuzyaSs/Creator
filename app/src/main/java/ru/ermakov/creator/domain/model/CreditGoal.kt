package ru.ermakov.creator.domain.model

import java.time.LocalDateTime

data class CreditGoal(
    val id: Long,
    val creator: Creator,
    val targetBalance: Long,
    val balance: Long,
    val description: String,
    val creationDate: LocalDateTime
)