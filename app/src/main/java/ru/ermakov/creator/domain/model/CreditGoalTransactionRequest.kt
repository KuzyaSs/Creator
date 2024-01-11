package ru.ermakov.creator.domain.model

data class CreditGoalTransactionRequest(
    val creditGoalId: Long,
    val senderUserId: String,
    val receiverUserId: String,
    val transactionTypeId: Long,
    val amount: Long,
    val message: String
)
