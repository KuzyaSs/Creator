package ru.ermakov.creator.util.exception

import ru.ermakov.creator.util.Constant.Companion.INVALID_USER_EXCEPTION

class InvalidUserException : Exception() {
    override val message: String
        get() = INVALID_USER_EXCEPTION
}