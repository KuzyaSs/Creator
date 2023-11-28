package ru.ermakov.creator.domain.useCase.changePassword

import ru.ermakov.creator.domain.exception.EmptyDataException
import ru.ermakov.creator.domain.exception.PasswordMismatchException
import ru.ermakov.creator.domain.repository.AuthRepository

class ChangePasswordUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        currentPassword: String,
        newPassword: String,
        confirmationNewPassword: String
    ) {
        if (currentPassword.isBlank() || newPassword.isBlank() || confirmationNewPassword.isBlank()) {
            throw EmptyDataException()
        }

        if (newPassword != confirmationNewPassword) {
            throw PasswordMismatchException()
        }
        authRepository.changePassword(currentPassword = currentPassword, newPassword = newPassword)
    }
}