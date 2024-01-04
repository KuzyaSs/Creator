package ru.ermakov.creator.domain.useCase.purchaseSubscription

import ru.ermakov.creator.domain.model.UserSubscriptionRequest
import ru.ermakov.creator.domain.repository.UserSubscriptionRepository

class PurchaseSubscriptionUseCase(private val userSubscriptionRepository: UserSubscriptionRepository) {
    suspend operator fun invoke(userSubscriptionRequest: UserSubscriptionRequest) {
        userSubscriptionRepository.insertUserSubscription(
            userSubscriptionRequest = userSubscriptionRequest
        )
    }
}