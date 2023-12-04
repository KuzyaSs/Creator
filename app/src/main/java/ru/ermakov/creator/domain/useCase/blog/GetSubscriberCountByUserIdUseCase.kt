package ru.ermakov.creator.domain.useCase.blog

import kotlinx.coroutines.delay

class GetSubscriberCountByUserIdUseCase {
    suspend operator fun invoke(userId: String): Long {
        delay(1500L)
        return 60
    }
}