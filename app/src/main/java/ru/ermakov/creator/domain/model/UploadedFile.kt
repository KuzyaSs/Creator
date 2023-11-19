package ru.ermakov.creator.domain.model

data class UploadedFile(
    val url: String = "",
    val progress: Double = 0.0,
)