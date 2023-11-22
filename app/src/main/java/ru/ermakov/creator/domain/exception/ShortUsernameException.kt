package ru.ermakov.creator.domain.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.SHORT_USERNAME_EXCEPTION

class ShortUsernameException : Exception() {
    override val message: String
        get() = SHORT_USERNAME_EXCEPTION
}