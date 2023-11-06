package ru.ermakov.creator.data.exception

import java.lang.Exception

interface ExceptionHandler {
    fun handleException(exception: Exception): String
}