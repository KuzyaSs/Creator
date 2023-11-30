package ru.ermakov.creator.domain.useCase.blog

import kotlinx.coroutines.delay

class GetNumFollowersByUserIdUseCase {
    suspend operator fun invoke(userId: String): Long {
        delay(1500L)
        return 250
    }
}