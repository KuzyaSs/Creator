package ru.ermakov.creator.domain.model

data class User(
    val id: String,
    val email: String,
    val username: String,
    val image: String,
    val money: Int,
    val about: String
)
