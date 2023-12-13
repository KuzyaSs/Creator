package ru.ermakov.creator.domain.model

data class SubscriptionRequest(
    val creatorId: String,
    val title: String,
    val description: String,
    val price: Int
)
