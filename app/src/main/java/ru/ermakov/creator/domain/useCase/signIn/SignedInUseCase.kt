package ru.ermakov.creator.domain.useCase.signIn

import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.data.repository.user.auth.AuthRepository
import ru.ermakov.creator.util.Resource

class SignedInUseCase(private val authRepository: AuthRepository) {
    suspend fun execute(): Resource<SignInData> {
        return authRepository.signedIn()
    }
}