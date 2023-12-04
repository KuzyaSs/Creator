package ru.ermakov.creator.domain.model

data class Creator(
    val user: User,
    val categories: List<Category>,
    val followerCount: Long,
    val subscriberCount: Long,
    val postCount: Long
)
