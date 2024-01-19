package ru.ermakov.creator.domain.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INVALID_CARD_NUMBER_LENGTH_EXCEPTION

class InvalidCardNumberLengthException : Exception() {
    override val message: String
        get() = INVALID_CARD_NUMBER_LENGTH_EXCEPTION
}