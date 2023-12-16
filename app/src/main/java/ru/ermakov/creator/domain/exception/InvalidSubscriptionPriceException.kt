package ru.ermakov.creator.domain.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INVALID_SUBSCRIPTION_PRICE_EXCEPTION

class InvalidSubscriptionPriceException : Exception() {
    override val message: String
        get() = INVALID_SUBSCRIPTION_PRICE_EXCEPTION
}