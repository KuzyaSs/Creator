package ru.ermakov.creator.presentation.exception

interface ExceptionLocalizer {
    fun localizeException(errorMessage: String): String
}