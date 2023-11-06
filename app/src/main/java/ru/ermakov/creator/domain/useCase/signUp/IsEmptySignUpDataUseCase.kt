package ru.ermakov.creator.domain.useCase.signUp

import ru.ermakov.creator.domain.model.SignUpData

class IsEmptySignUpDataUseCase {
    fun execute(signUpData: SignUpData): Boolean {
        signUpData.apply {
            return email.isBlank() || password.isBlank() || confirmationPassword.isBlank()
        }
    }
}