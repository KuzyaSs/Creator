package ru.ermakov.creator.domain.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INVALID_CVV_LENGTH_EXCEPTION

class InvalidCvvLengthException : Exception() {
    override val message: String
        get() = INVALID_CVV_LENGTH_EXCEPTION
}