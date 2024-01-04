package ru.ermakov.creator.domain.useCase.shared

import kotlinx.coroutines.delay

class GetBalanceByUserIdUseCase {
    suspend operator fun invoke(userId: String): Long {
        delay(1000L)
        return 150
    }
}