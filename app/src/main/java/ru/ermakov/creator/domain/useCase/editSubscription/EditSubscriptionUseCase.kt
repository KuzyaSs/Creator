package ru.ermakov.creator.domain.useCase.editSubscription

import ru.ermakov.creator.domain.exception.EmptyDataException
import ru.ermakov.creator.domain.exception.InvalidSubscriptionPriceException
import ru.ermakov.creator.domain.model.SubscriptionRequest
import ru.ermakov.creator.domain.repository.SubscriptionRepository

class EditSubscriptionUseCase(private val subscriptionRepository: SubscriptionRepository) {
    suspend operator fun invoke(subscriptionId: Long, subscriptionRequest: SubscriptionRequest) {
        if (subscriptionRequest.title.isBlank() || subscriptionRequest.description.isBlank()) {
            throw EmptyDataException()
        }
        if (subscriptionRequest.price <= 0) {
            throw InvalidSubscriptionPriceException()
        }

        subscriptionRepository.updateSubscription(
            subscriptionId = subscriptionId,
            subscriptionRequest = subscriptionRequest
        )
    }
}