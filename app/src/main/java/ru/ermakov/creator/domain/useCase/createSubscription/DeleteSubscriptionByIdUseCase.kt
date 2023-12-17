package ru.ermakov.creator.domain.useCase.createSubscription

import ru.ermakov.creator.domain.repository.SubscriptionRepository

class DeleteSubscriptionByIdUseCase(private val subscriptionRepository: SubscriptionRepository) {
    suspend operator fun invoke(subscriptionId: Long) {
        subscriptionRepository.deleteSubscriptionById(subscriptionId = subscriptionId)
    }
}