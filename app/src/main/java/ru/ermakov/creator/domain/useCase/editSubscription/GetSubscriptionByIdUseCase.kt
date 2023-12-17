package ru.ermakov.creator.domain.useCase.editSubscription

import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.domain.repository.SubscriptionRepository

class GetSubscriptionByIdUseCase(private val subscriptionRepository: SubscriptionRepository) {
    suspend operator fun invoke(subscriptionId: Long): Subscription {
        return subscriptionRepository.getSubscriptionById(subscriptionId = subscriptionId)
    }
}