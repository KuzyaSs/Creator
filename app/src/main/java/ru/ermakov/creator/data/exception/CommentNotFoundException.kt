package ru.ermakov.creator.data.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.COMMENT_NOT_FOUND_EXCEPTION

class CommentNotFoundException : Exception() {
    override val message: String
        get() = COMMENT_NOT_FOUND_EXCEPTION
}