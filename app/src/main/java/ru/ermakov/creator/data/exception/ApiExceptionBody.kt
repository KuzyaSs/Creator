package ru.ermakov.creator.data.exception

data class ApiExceptionBody(
    val timestamp: String,
    val status: Int,
    val error: String,
    val path: String
)