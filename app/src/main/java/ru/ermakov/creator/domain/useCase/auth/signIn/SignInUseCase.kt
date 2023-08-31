package ru.ermakov.creator.domain.useCase.auth.signIn

import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.util.Constant
import ru.ermakov.creator.util.Resource

class SignInUseCase(
    private val authRepository: AuthRepository,
    private val isEmptySignInDataUseCase: IsEmptySignInDataUseCase
) {
    suspend fun execute(signInData: SignInData): Resource<SignInData> {
        if (isEmptySignInDataUseCase.execute(signInData = signInData)) {
            return Resource.Error(data = null, message = Constant.EMPTY_DATA_EXCEPTION)
        }
        return authRepository.signIn(signInData = signInData)
    }
}