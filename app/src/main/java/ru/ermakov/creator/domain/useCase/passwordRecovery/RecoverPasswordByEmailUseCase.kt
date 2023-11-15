package ru.ermakov.creator.domain.useCase.passwordRecovery

import ru.ermakov.creator.domain.exception.EmptyDataException
import ru.ermakov.creator.domain.repository.AuthRepository

class RecoverPasswordByEmailUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String) {
        if (email.isBlank()) {
            throw EmptyDataException()
        }
        authRepository.resetPassword(email = email)
    }
}