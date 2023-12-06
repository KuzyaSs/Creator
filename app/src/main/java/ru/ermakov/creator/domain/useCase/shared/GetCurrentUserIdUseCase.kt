package ru.ermakov.creator.domain.useCase.shared

import ru.ermakov.creator.domain.repository.AuthRepository

class GetCurrentUserIdUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): String {
        return authRepository.getCurrentUserId()
    }
}