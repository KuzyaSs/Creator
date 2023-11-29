package ru.ermakov.creator.domain.useCase.blog

import kotlinx.coroutines.delay

class IsSubscriberUseCase {
    suspend operator fun invoke(): Boolean {
        delay(1000L)
        return false
    }
}