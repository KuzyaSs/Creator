package ru.ermakov.creator.domain.useCase.signUp

import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.util.Constant.Companion.EMPTY_DATA_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.PASSWORDS_DON_T_MATCH_EXCEPTION
import ru.ermakov.creator.util.Resource

class SignUpUseCase(
    private val authRepository: AuthRepository,
    private val isEmptyDataUseCase: IsEmptyDataUseCase,
    private val passwordsAreTheSameUseCase: PasswordsAreTheSameUseCase
) {
    suspend fun execute(signUpData: SignUpData): Resource<User> {
        if (isEmptyDataUseCase.execute(signUpData = signUpData)) {
            return Resource.Error(data = null, message = EMPTY_DATA_EXCEPTION)
        }
        if (!passwordsAreTheSameUseCase.execute(signUpData = signUpData)) {
            return Resource.Error(data = null, message = PASSWORDS_DON_T_MATCH_EXCEPTION)
        }
        return authRepository.signUp(signUpData = signUpData)
    }
}