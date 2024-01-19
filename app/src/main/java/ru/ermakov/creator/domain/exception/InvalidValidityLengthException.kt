package ru.ermakov.creator.domain.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INVALID_VALIDITY_LENGTH_EXCEPTION

class InvalidValidityLengthException : Exception() {
    override val message: String
        get() = INVALID_VALIDITY_LENGTH_EXCEPTION
}