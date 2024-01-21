package ru.ermakov.creator.domain.useCase.createCreditGoal

import ru.ermakov.creator.domain.exception.EmptyDataException
import ru.ermakov.creator.domain.exception.InvalidCreditGoalTargetBalanceException
import ru.ermakov.creator.domain.model.CreditGoalRequest
import ru.ermakov.creator.domain.repository.CreditGoalRepository

class CreateCreditGoalUseCase(private val creditGoalRepository: CreditGoalRepository) {
    suspend operator fun invoke(creditGoalRequest: CreditGoalRequest) {
        if (creditGoalRequest.description.isBlank()) {
            throw EmptyDataException()
        }
        if (creditGoalRequest.targetBalance <= 0) {
            throw InvalidCreditGoalTargetBalanceException()
        }
        creditGoalRepository.insertCreditGoal(creditGoalRequest = creditGoalRequest)
    }
}