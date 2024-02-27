package ru.ermakov.creator.data.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.DUPLICATE_TAG_NAME_EXCEPTION

class DuplicateTagNameException : Exception() {
    override val message: String
        get() = DUPLICATE_TAG_NAME_EXCEPTION
}