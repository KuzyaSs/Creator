package ru.ermakov.creator.domain.useCase.createSubscription

import ru.ermakov.creator.domain.exception.EmptyDataException
import ru.ermakov.creator.domain.exception.InvalidSubscriptionPriceException
import ru.ermakov.creator.domain.model.SubscriptionRequest
import ru.ermakov.creator.domain.repository.SubscriptionRepository

class CreateSubscriptionUseCase(private val subscriptionRepository: SubscriptionRepository) {
    suspend operator fun invoke(subscriptionRequest: SubscriptionRequest) {
        if (subscriptionRequest.title.isBlank() || subscriptionRequest.description.isBlank()) {
            throw EmptyDataException()
        }
        if (subscriptionRequest.price <= 0) {
            throw InvalidSubscriptionPriceException()
        }
        subscriptionRepository.insertSubscription(subscriptionRequest = subscriptionRequest)
    }
}