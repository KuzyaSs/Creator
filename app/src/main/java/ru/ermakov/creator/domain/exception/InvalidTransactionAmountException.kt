package ru.ermakov.creator.domain.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INVALID_TRANSACTION_AMOUNT_EXCEPTION

class InvalidTransactionAmountException : Exception() {
    override val message: String
        get() = INVALID_TRANSACTION_AMOUNT_EXCEPTION
}