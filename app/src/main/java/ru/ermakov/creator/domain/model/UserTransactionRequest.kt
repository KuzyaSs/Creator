package ru.ermakov.creator.domain.model

data class UserTransactionRequest(
    val senderUserId: String,
    val receiverUserId: String,
    val transactionTypeId: Long,
    val amount: Long,
    val message: String
)