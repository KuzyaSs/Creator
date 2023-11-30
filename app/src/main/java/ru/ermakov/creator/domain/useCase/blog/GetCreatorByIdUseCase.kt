package ru.ermakov.creator.domain.useCase.blog

import ru.ermakov.creator.domain.model.Creator
import ru.ermakov.creator.domain.useCase.common.GetCategoriesByUserIdUseCase
import ru.ermakov.creator.domain.useCase.common.GetUserByIdUseCase

class GetCreatorByIdUseCase(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getCategoriesByUserIdUseCase: GetCategoriesByUserIdUseCase,
    private val getNumFollowersByUserIdUseCase: GetNumFollowersByUserIdUseCase,
    private val getNumSubscribersByUserIdUseCase: GetNumSubscribersByUserIdUseCase,
    private val getNumPostsByUserIdUseCase: GetNumPostsByUserIdUseCase,
) {
    suspend operator fun invoke(creatorId: String): Creator {
        return Creator(
            user = getUserByIdUseCase(userId = creatorId),
            categories = getCategoriesByUserIdUseCase(userId = creatorId),
            getNumFollowersByUserIdUseCase(userId = creatorId),
            getNumSubscribersByUserIdUseCase(userId = creatorId),
            getNumPostsByUserIdUseCase(userId = creatorId)
        )
    }
}