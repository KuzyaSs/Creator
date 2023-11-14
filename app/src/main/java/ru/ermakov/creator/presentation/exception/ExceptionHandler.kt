package ru.ermakov.creator.presentation.exception

import java.lang.Exception

interface ExceptionHandler {
    fun handleException(exception: Exception): String
}