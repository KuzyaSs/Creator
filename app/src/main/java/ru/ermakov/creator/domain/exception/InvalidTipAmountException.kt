package ru.ermakov.creator.domain.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INVALID_TIP_AMOUNT_EXCEPTION

class InvalidTipAmountException : Exception() {
    override val message: String
        get() = INVALID_TIP_AMOUNT_EXCEPTION
}