package ru.ermakov.creator.presentation.util

interface ExceptionHandler {
    fun handleException(exception: Throwable): String
}