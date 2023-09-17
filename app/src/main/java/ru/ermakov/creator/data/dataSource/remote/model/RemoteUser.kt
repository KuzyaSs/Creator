package ru.ermakov.creator.data.dataSource.remote.model

data class RemoteUser(
    val email: String = "",
    val username: String = "",
    val profileAvatar: String = "",
    val profileBackground: String = "",
    val about: String = ""
)