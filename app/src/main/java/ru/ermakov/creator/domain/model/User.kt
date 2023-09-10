package ru.ermakov.creator.domain.model

data class User(
    val id: String,
    val email: String,
    val username: String,
    val money: Int,
    val profileAvatar: String,
    val profileBackground: String,
    val about: String
)
