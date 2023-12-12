package ru.ermakov.creator.domain.model

import java.time.LocalDateTime

data class User(
    val id: String,
    val username: String,
    val email: String,
    val bio: String,
    val profileAvatarUrl: String,
    val profileBackgroundUrl: String,
    val isModerator: Boolean,
    val registrationDate: LocalDateTime
)
