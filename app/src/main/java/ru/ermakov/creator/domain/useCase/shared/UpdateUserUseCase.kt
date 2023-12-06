package ru.ermakov.creator.domain.useCase.shared

import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.domain.repository.UserRepository

class UpdateUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: User) {
        userRepository.updateUser(user = user)
    }
}