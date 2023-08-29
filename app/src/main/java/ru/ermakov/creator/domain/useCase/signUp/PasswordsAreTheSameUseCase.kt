package ru.ermakov.creator.domain.useCase.signUp

import ru.ermakov.creator.domain.model.SignUpData

class PasswordsAreTheSameUseCase {
    fun execute(signUpData: SignUpData): Boolean {
        signUpData.apply {
            return password == confirmationPassword
        }
    }
}