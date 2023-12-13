package ru.ermakov.creator.domain.model

data class UserSubscriptionRequest(
    val subscriptionId: Long,
    val userId: String,
    val durationInMonths: Int
)
