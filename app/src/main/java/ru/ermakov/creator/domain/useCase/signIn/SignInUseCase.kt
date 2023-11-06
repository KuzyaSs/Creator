package ru.ermakov.creator.domain.useCase.signIn

import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.data.repository.user.auth.AuthRepository
import ru.ermakov.creator.util.Constant.Companion.EMPTY_DATA_EXCEPTION
import ru.ermakov.creator.util.Resource

class SignInUseCase(
    private val authRepository: AuthRepository,
    private val isEmptySignInDataUseCase: IsEmptySignInDataUseCase
) {
    suspend fun execute(signInData: SignInData): Resource<SignInData> {
        if (isEmptySignInDataUseCase.execute(signInData = signInData)) {
            return Resource.Error(data = null, message = EMPTY_DATA_EXCEPTION)
        }
        return authRepository.signIn(signInData = signInData)
    }
}