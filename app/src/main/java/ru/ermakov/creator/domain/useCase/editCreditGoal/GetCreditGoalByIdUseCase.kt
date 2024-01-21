package ru.ermakov.creator.domain.useCase.editCreditGoal

import ru.ermakov.creator.domain.model.CreditGoal
import ru.ermakov.creator.domain.repository.CreditGoalRepository

class GetCreditGoalByIdUseCase(private val creditGoalRepository: CreditGoalRepository) {
    suspend operator fun invoke(creditGoalId: Long): CreditGoal {
        return creditGoalRepository.getCreditGoalById(creditGoalId = creditGoalId)
    }
}