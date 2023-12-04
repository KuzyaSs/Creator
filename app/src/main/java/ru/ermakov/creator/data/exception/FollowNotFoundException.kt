package ru.ermakov.creator.data.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.FOLLOW_NOT_FOUND_EXCEPTION

class FollowNotFoundException : Exception() {
    override val message: String
        get() = FOLLOW_NOT_FOUND_EXCEPTION
}