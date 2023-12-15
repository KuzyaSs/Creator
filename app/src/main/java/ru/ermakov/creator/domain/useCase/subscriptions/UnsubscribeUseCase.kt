package ru.ermakov.creator.domain.useCase.subscriptions

import ru.ermakov.creator.domain.repository.UserSubscriptionRepository

class UnsubscribeUseCase(
    private val userSubscriptionRepository: UserSubscriptionRepository
) {
    suspend operator fun invoke(userId: String, userSubscriptionId: Long) {
        return userSubscriptionRepository.deleteUserSubscriptionById(
            userId = userId,
            userSubscriptionId = userSubscriptionId
        )
    }
}