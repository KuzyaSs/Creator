package ru.ermakov.creator.domain.useCase.signIn

import ru.ermakov.creator.domain.exception.EmptyDataException
import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.domain.repository.AuthRepository

class SignInUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(signInData: SignInData) {
        if (signInData.email.isBlank() || signInData.password.isBlank()) {
            throw EmptyDataException()
        }
        authRepository.signIn(signInData = signInData)
    }
}