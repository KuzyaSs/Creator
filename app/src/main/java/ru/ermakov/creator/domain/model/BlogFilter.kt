package ru.ermakov.creator.domain.model

data class BlogFilter(
    val postType: String,
    val tagIds: List<Long>
)