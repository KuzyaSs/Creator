package ru.ermakov.creator.domain.useCase.editCreditGoal

import ru.ermakov.creator.domain.exception.EmptyDataException
import ru.ermakov.creator.domain.exception.InvalidCreditGoalTargetBalanceException
import ru.ermakov.creator.domain.model.CreditGoalRequest
import ru.ermakov.creator.domain.repository.CreditGoalRepository

class EditCreditGoalUseCase(private val creditGoalRepository: CreditGoalRepository) {
    suspend operator fun invoke(creditGoalId: Long, creditGoalRequest: CreditGoalRequest) {
        if (creditGoalRequest.description.isBlank()) {
            throw EmptyDataException()
        }
        if (creditGoalRequest.targetBalance <= 0) {
            throw InvalidCreditGoalTargetBalanceException()
        }

        creditGoalRepository.updateCreditGoal(
            creditGoalId = creditGoalId,
            creditGoalRequest = creditGoalRequest
        )
    }
}