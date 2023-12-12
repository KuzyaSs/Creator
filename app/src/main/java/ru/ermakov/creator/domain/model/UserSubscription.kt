package ru.ermakov.creator.domain.model

import java.time.LocalDateTime

data class UserSubscription(
    val id: Long,
    val subscription: Subscription,
    val user: User,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime
)
