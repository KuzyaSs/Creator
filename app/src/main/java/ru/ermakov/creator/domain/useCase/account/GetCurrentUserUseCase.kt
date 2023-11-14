package ru.ermakov.creator.domain.useCase.account

import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.domain.repository.UserRepository

class GetCurrentUserUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User {
        val currentUserId = authRepository.getCurrentUserId()
        return userRepository.getUserById(userId = currentUserId)
    }
}