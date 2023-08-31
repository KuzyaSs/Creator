package ru.ermakov.creator.domain.useCase.auth.signUp

import ru.ermakov.creator.domain.model.SignUpData

class IsEmptySignUpDataUseCase {
    fun execute(signUpData: SignUpData): Boolean {
        signUpData.apply {
            return email.isBlank() || password.isBlank() || confirmationPassword.isBlank()
        }
    }
}