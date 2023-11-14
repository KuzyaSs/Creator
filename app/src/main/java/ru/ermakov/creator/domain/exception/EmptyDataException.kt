package ru.ermakov.creator.domain.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.EMPTY_DATA_EXCEPTION
import kotlin.Exception

class EmptyDataException : Exception() {
    override val message: String
        get() = EMPTY_DATA_EXCEPTION
}