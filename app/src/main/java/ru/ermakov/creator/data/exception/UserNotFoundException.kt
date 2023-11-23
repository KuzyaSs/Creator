package ru.ermakov.creator.data.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.USER_NOT_FOUND_EXCEPTION

class UserNotFoundException : Exception() {
    override val message: String
        get() = USER_NOT_FOUND_EXCEPTION
}