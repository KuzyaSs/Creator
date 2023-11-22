package ru.ermakov.creator.domain.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.UNKNOWN_EXCEPTION
import kotlin.Exception

class UnexpectedValueException : Exception() {
    override val message: String
        get() = UNKNOWN_EXCEPTION
}