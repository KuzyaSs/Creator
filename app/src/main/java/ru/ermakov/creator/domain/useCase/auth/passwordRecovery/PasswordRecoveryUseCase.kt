package ru.ermakov.creator.domain.useCase.auth.passwordRecovery

import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.util.Constant.Companion.EMPTY_DATA_EXCEPTION
import ru.ermakov.creator.util.Resource

class PasswordRecoveryUseCase(private val authRepository: AuthRepository) {
    suspend fun execute(email: String): Resource<String> {
        if (email.isBlank()) {
            return Resource.Error(data = null, message = EMPTY_DATA_EXCEPTION)
        }
        return authRepository.resetPassword(email = email)
    }
}