package ru.ermakov.creator.domain.useCase.common

import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.domain.repository.UserRepository

class GetCurrentUserUseCase(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User {
        return userRepository.getUserById(userId = getCurrentUserIdUseCase())
    }
}