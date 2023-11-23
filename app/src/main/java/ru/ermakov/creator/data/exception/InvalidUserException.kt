package ru.ermakov.creator.data.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.UNKNOWN_EMAIL_EXCEPTION

class InvalidUserException : Exception() {
    override val message: String
        get() = UNKNOWN_EMAIL_EXCEPTION
}