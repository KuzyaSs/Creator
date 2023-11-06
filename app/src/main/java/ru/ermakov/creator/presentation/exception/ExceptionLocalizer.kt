package ru.ermakov.creator.presentation.exception

interface ExceptionLocalizer {
    fun localizeException(message: String): String
}