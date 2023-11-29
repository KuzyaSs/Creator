package ru.ermakov.creator.domain.useCase.common

import ru.ermakov.creator.domain.repository.AuthRepository

class GetCurrentUserIdUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): String {
        return authRepository.getCurrentUserId()
    }
}