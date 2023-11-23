package ru.ermakov.creator.data.exception

import ru.ermakov.creator.domain.exception.ErrorConstants

class ApiExceptionLocalizerImpl : ApiExceptionLocalizer {
    override fun localizeApiException(apiExceptionBody: ApiExceptionBody): Exception {
        return when (apiExceptionBody.error) {
            ErrorConstants.USERNAME_IN_USE_EXCEPTION -> {
                UsernameInUseException()
            }

            ErrorConstants.USER_NOT_FOUND_EXCEPTION -> {
                UserNotFoundException()
            }

            else -> {
                Exception()
            }
        }
    }
}