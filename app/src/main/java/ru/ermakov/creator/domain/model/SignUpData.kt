package ru.ermakov.creator.domain.model

data class SignUpData(
    val email: String,
    val password: String,
    val confirmationPassword: String
)