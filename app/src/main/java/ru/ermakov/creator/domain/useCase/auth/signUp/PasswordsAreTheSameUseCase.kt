package ru.ermakov.creator.domain.useCase.auth.signUp

import ru.ermakov.creator.domain.model.SignUpData

class PasswordsAreTheSameUseCase {
    fun execute(signUpData: SignUpData): Boolean {
        signUpData.apply {
            return password == confirmationPassword
        }
    }
}