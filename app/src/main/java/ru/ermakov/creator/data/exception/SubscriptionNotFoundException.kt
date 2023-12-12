package ru.ermakov.creator.data.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.SUBSCRIPTION_NOT_FOUND_EXCEPTION

class SubscriptionNotFoundException : Exception() {
    override val message: String
        get() = SUBSCRIPTION_NOT_FOUND_EXCEPTION
}