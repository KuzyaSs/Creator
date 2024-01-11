package ru.ermakov.creator.domain.model

data class CreditGoalRequest(
    val creatorId: String,
    val targetBalance: Long,
    val description: String
)