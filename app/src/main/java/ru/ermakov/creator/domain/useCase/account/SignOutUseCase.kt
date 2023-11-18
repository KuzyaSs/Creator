package ru.ermakov.creator.domain.useCase.account

import ru.ermakov.creator.domain.repository.AuthRepository

class SignOutUseCase(private val authRepository: AuthRepository) {
    operator fun invoke() {
        authRepository.signOut()
    }
}