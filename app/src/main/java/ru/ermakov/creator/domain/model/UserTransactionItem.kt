package ru.ermakov.creator.domain.model

data class UserTransactionItem(
    val id: Long,
    val title: String,
    val user: User,
    val transactionType: String,
    val amount: Long,
    val message: String,
    val date: String
)
