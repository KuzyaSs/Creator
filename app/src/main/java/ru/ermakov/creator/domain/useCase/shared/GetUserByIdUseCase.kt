package ru.ermakov.creator.domain.useCase.shared

import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.domain.repository.UserRepository

class GetUserByIdUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(userId: String): User {
        return userRepository.getUserById(userId = userId)
    }
}