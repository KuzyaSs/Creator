package ru.ermakov.creator.domain.useCase.auth.signIn

import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.util.Resource

class SignedInUseCase(private val authRepository: AuthRepository) {
    suspend fun execute(): Resource<SignInData> {
        return authRepository.signedIn()
    }
}