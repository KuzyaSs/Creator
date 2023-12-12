package ru.ermakov.creator.data.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.DUPLICATE_SUBSCRIPTION_TITLE_EXCEPTION

class DuplicateSubscriptionTitleException : Exception() {
    override val message: String
        get() = DUPLICATE_SUBSCRIPTION_TITLE_EXCEPTION
}