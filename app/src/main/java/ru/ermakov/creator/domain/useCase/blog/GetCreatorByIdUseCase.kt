package ru.ermakov.creator.domain.useCase.blog

import ru.ermakov.creator.domain.model.Creator
import ru.ermakov.creator.domain.useCase.common.GetCategoriesByUserIdUseCase
import ru.ermakov.creator.domain.useCase.common.GetUserByIdUseCase

class GetCreatorByIdUseCase(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getCategoriesByUserIdUseCase: GetCategoriesByUserIdUseCase,
    private val getFollowerCountByUserIdUseCase: GetFollowerCountByUserIdUseCase,
    private val getSubscriberCountByUserIdUseCase: GetSubscriberCountByUserIdUseCase,
    private val getPostCountByUserIdUseCase: GetPostCountByUserIdUseCase,
) {
    suspend operator fun invoke(creatorId: String): Creator {
        return Creator(
            user = getUserByIdUseCase(userId = creatorId),
            categories = getCategoriesByUserIdUseCase(userId = creatorId),
            getFollowerCountByUserIdUseCase(userId = creatorId),
            getSubscriberCountByUserIdUseCase(userId = creatorId),
            getPostCountByUserIdUseCase(userId = creatorId)
        )
    }
}