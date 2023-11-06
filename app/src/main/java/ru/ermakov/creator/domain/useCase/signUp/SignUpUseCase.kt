package ru.ermakov.creator.domain.useCase.signUp

import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.data.repository.user.auth.AuthRepository
import ru.ermakov.creator.util.Constant.Companion.EMPTY_DATA_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.PASSWORDS_DON_T_MATCH_EXCEPTION
import ru.ermakov.creator.util.Resource

class SignUpUseCase(
    private val authRepository: AuthRepository,
    private val isEmptySignUpDataUseCase: IsEmptySignUpDataUseCase,
    private val passwordsAreTheSameUseCase: PasswordsAreTheSameUseCase
) {
    suspend fun execute(signUpData: SignUpData): Resource<SignUpData> {
        if (isEmptySignUpDataUseCase.execute(signUpData = signUpData)) {
            return Resource.Error(data = null, message = EMPTY_DATA_EXCEPTION)
        }
        if (!passwordsAreTheSameUseCase.execute(signUpData = signUpData)) {
            return Resource.Error(data = null, message = PASSWORDS_DON_T_MATCH_EXCEPTION)
        }
        return authRepository.signUp(signUpData = signUpData)
    }
}