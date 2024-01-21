package ru.ermakov.creator.data.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.CREDIT_GOAL_NOT_FOUND_EXCEPTION

class CreditGoalNotFoundException : Exception() {
    override val message: String
        get() = CREDIT_GOAL_NOT_FOUND_EXCEPTION
}