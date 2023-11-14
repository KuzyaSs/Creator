package ru.ermakov.creator.domain.useCase.signIn

import ru.ermakov.creator.domain.repository.AuthRepository

class SignedInUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke() {
        authRepository.signedIn()
    }
}