package ru.ermakov.creator.domain.exception

import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INVALID_CREDIT_GOAL_TARGET_BALANCE_EXCEPTION

class InvalidCreditGoalTargetBalanceException : Exception() {
    override val message: String
        get() = INVALID_CREDIT_GOAL_TARGET_BALANCE_EXCEPTION
}