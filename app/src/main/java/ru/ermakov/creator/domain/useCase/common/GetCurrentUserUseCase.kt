package ru.ermakov.creator.domain.useCase.common

import kotlinx.coroutines.delay
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.domain.repository.UserRepository
import java.time.LocalDate

class GetCurrentUserUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User {
        val currentUserId = authRepository.getCurrentUserId()
        return userRepository.getUserById(userId = currentUserId)
    }
}