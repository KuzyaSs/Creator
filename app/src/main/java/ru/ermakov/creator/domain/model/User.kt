package ru.ermakov.creator.domain.model

import java.time.LocalDate

data class User(
    val id: String,
    val username: String,
    val email: String,
    val bio: String,
    val profileAvatarUrl: String,
    val profileBackgroundUrl: String,
    val registrationDate: LocalDate
)
