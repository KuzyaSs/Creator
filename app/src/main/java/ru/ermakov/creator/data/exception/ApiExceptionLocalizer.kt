package ru.ermakov.creator.data.exception

import retrofit2.Response

interface ApiExceptionLocalizer {
    fun localizeApiException(response: Response<*>): Exception
}