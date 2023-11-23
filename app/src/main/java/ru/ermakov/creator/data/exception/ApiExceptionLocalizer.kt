package ru.ermakov.creator.data.exception

interface ApiExceptionLocalizer {
    fun localizeApiException(apiExceptionBody: ApiExceptionBody): Exception
}