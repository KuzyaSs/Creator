package ru.ermakov.creator.domain.useCase.subscriptions

import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.domain.repository.SubscriptionRepository

class GetSubscriptionsByCreatorIdUseCase(private val subscriptionRepository: SubscriptionRepository) {
    suspend operator fun invoke(creatorId: String): List<Subscription> {
        return subscriptionRepository.getSubscriptionsByCreatorId(creatorId = creatorId)
    }
}