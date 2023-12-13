package ru.ermakov.creator.domain.useCase.subscriptions

import ru.ermakov.creator.domain.model.UserSubscription
import ru.ermakov.creator.domain.repository.UserSubscriptionRepository

class GetUserSubscriptionsByUserAndCreatorIdsUseCase(
    private val userSubscriptionRepository: UserSubscriptionRepository
) {
    suspend operator fun invoke(userId: String, creatorId: String): List<UserSubscription> {
        return userSubscriptionRepository.getUserSubscriptionsByUserAndCreatorIds(
            userId = userId,
            creatorId = creatorId
        )
    }
}