package ru.ermakov.creator.domain.model

import java.time.LocalDateTime

data class CreditGoalTransaction(
    val id: Long,
    val creditGoal: CreditGoal,
    val user: User,
    val transactionType: TransactionType,
    val amount: Long,
    val message: String,
    val transactionDate: LocalDateTime
)
