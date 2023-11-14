package ru.ermakov.creator.domain.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.PASSWORD_MISMATCH_EXCEPTION

class PasswordMismatchException : Exception() {
    override val message: String
        get() = PASSWORD_MISMATCH_EXCEPTION
}