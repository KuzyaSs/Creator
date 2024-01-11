package ru.ermakov.creator.domain.model

import java.time.LocalDateTime

data class UserTransaction(
    val id: Long,
    val senderUser: User,
    val receiverUser: User,
    val transactionType: TransactionType,
    val amount: Long,
    val message: String,
    val transactionDate: LocalDateTime
)