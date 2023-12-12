package ru.ermakov.creator.domain.model

data class Subscription(
    val id: Long,
    val creator: Creator,
    val title: String,
    val description: String,
    val price: Int
)
