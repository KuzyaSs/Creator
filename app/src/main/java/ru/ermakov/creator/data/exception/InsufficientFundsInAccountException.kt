package ru.ermakov.creator.data.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INSUFFICIENT_FUNDS_IN_ACCOUNT_EXCEPTION

class InsufficientFundsInAccountException : Exception() {
    override val message: String
        get() = INSUFFICIENT_FUNDS_IN_ACCOUNT_EXCEPTION
}