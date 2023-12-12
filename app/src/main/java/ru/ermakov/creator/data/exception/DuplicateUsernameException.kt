package ru.ermakov.creator.data.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.DUPLICATE_USERNAME_EXCEPTION

class DuplicateUsernameException : Exception() {
    override val message: String
        get() = DUPLICATE_USERNAME_EXCEPTION
}