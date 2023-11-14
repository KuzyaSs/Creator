package ru.ermakov.creator.domain.useCase.signUp

import ru.ermakov.creator.domain.repository.UserRepository
import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.domain.exception.EmptyDataException
import ru.ermakov.creator.domain.exception.PasswordMismatchException

class SignUpUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(signUpData: SignUpData) {
        if (signUpData.email.isBlank() ||
            signUpData.password.isBlank() ||
            signUpData.confirmationPassword.isBlank()
        ) {
            throw EmptyDataException()
        }

        if (signUpData.password != signUpData.confirmationPassword) {
            throw PasswordMismatchException()
        }

        val authUser = authRepository.signUp(signUpData = signUpData)
        userRepository.insertUser(authUser = authUser)
    }
}