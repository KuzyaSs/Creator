package ru.ermakov.creator.domain.useCase.signUp

import ru.ermakov.creator.data.repository.user.UserRepository
import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.data.repository.user.auth.AuthRepository
import ru.ermakov.creator.util.Constant.Companion.EMPTY_DATA_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.PASSWORDS_DON_T_MATCH_EXCEPTION
import ru.ermakov.creator.util.Resource

class SignUpUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend fun execute(signUpData: SignUpData): Resource<SignUpData> {
        if (signUpData.email.isBlank() ||
            signUpData.password.isBlank() ||
            signUpData.confirmationPassword.isBlank()
        ) {
            return Resource.Error(data = null, message = EMPTY_DATA_EXCEPTION)
        }
        if (signUpData.password != signUpData.confirmationPassword) {
            return Resource.Error(data = null, message = PASSWORDS_DON_T_MATCH_EXCEPTION)
        }

        val authUserRemoteResource = authRepository.signUp(signUpData = signUpData)
        authUserRemoteResource.data?.let {
            userRepository.insertUser(authUserRemote = authUserRemoteResource.data)
            return Resource.Success(data = signUpData)
        }
    }
}