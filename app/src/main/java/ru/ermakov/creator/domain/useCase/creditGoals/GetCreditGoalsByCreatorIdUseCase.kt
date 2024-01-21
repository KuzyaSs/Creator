package ru.ermakov.creator.domain.useCase.creditGoals

import ru.ermakov.creator.domain.model.CreditGoal
import ru.ermakov.creator.domain.repository.CreditGoalRepository

class GetCreditGoalsByCreatorIdUseCase(private val creditGoalRepository: CreditGoalRepository) {
    suspend operator fun invoke(creatorId: String): List<CreditGoal> {
        return creditGoalRepository.getCreditGoalsByCreatorId(creatorId = creatorId)
    }
}