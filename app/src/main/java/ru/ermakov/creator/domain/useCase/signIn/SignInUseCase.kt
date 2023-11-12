package ru.ermakov.creator.domain.useCase.signIn

import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.data.repository.user.auth.AuthRepository
import ru.ermakov.creator.util.Constant.Companion.EMPTY_DATA_EXCEPTION
import ru.ermakov.creator.util.Resource

class SignInUseCase(private val authRepository: AuthRepository) {
    suspend fun execute(signInData: SignInData): Resource<SignInData> {
        if (signInData.email.isBlank() || signInData.password.isBlank()) {
            return Resource.Error(data = null, message = EMPTY_DATA_EXCEPTION)
        }
        return authRepository.signIn(signInData = signInData)
    }
}