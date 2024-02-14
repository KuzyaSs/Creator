package ru.ermakov.creator.domain.model

data class FeedFilter(
    val postType: String,
    val categoryIds: List<Long>
)