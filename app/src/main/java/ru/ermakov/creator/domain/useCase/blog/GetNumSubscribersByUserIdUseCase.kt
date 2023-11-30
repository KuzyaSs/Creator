package ru.ermakov.creator.domain.useCase.blog

import kotlinx.coroutines.delay

class GetNumSubscribersByUserIdUseCase {
    suspend operator fun invoke(userId: String): Long {
        delay(1500L)
        return 60
    }
}