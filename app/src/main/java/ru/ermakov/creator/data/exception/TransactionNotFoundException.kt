package ru.ermakov.creator.data.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.TRANSACTION_NOT_FOUND_EXCEPTION

class TransactionNotFoundException : Exception() {
    override val message: String
        get() = TRANSACTION_NOT_FOUND_EXCEPTION
}