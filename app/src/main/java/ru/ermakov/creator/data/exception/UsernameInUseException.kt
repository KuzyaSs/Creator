package ru.ermakov.creator.data.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.USERNAME_IN_USE_EXCEPTION

class UsernameInUseException : Exception() {
    override val message: String
        get() = USERNAME_IN_USE_EXCEPTION
}