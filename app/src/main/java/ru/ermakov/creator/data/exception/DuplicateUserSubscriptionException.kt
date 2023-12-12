package ru.ermakov.creator.data.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.DUPLICATE_USER_SUBSCRIPTION_EXCEPTION

class DuplicateUserSubscriptionException : Exception() {
    override val message: String
        get() = DUPLICATE_USER_SUBSCRIPTION_EXCEPTION
}