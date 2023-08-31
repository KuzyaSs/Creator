package ru.ermakov.creator.data.storage.remote.model

data class RemoteUser(
    val email: String,
    val username: String,
    val image: String,
    val backgroundImage: String,
    val about: String
)