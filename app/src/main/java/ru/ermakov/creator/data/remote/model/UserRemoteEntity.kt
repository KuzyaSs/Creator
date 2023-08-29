package ru.ermakov.creator.data.remote.model

data class UserRemoteEntity(
    val email: String,
    val username: String,
    val image: String,
    val money: Int,
    val about: String
)