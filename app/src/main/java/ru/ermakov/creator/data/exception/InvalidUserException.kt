package ru.ermakov.creator.data.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INVALID_USER_EXCEPTION

class InvalidUserException : Exception() {
    override val message: String
        get() = INVALID_USER_EXCEPTION
}