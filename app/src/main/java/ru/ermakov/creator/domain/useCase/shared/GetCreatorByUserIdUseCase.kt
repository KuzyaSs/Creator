package ru.ermakov.creator.domain.useCase.shared

import ru.ermakov.creator.domain.model.Creator
import ru.ermakov.creator.domain.repository.CreatorRepository

class GetCreatorByUserIdUseCase(private val creatorRepository: CreatorRepository) {
    suspend operator fun invoke(userId: String): Creator {
        return creatorRepository.getCreatorByUserId(userId = userId)
    }
}