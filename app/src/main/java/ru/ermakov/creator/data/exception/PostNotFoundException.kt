package ru.ermakov.creator.data.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.POST_NOT_FOUND_EXCEPTION

class PostNotFoundException : Exception() {
    override val message: String
        get() = POST_NOT_FOUND_EXCEPTION
}