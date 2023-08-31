package ru.ermakov.creator.domain.useCase.auth.signIn

import ru.ermakov.creator.domain.model.SignInData

class IsEmptySignInDataUseCase {
    fun execute(signInData: SignInData): Boolean {
        signInData.apply {
            return email.isBlank() || password.isBlank()
        }
    }
}