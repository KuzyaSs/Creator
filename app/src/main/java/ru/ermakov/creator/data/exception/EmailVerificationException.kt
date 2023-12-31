package ru.ermakov.creator.data.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.EMAIL_VERIFICATION_EXCEPTION

class EmailVerificationException : Exception() {
    override val message: String
        get() = EMAIL_VERIFICATION_EXCEPTION
}