package ru.ermakov.creator.domain.useCase.blog

import ru.ermakov.creator.domain.useCase.subscriptions.GetUserSubscriptionsByUserAndCreatorIdsUseCase

class IsSubscriberUseCase(
    private val getUserSubscriptionsByUserAndCreatorIdsUseCase: GetUserSubscriptionsByUserAndCreatorIdsUseCase
) {
    suspend operator fun invoke(userId: String, creatorId: String): Boolean {
        return getUserSubscriptionsByUserAndCreatorIdsUseCase(
            userId = userId,
            creatorId = creatorId
        ).isNotEmpty()
    }
}