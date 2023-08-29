package ru.ermakov.creator.domain.repository

import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.util.Resource

interface AuthRepository {
    suspend fun signUp(signUpData: SignUpData): Resource<User>
    // fun validateEmail(signUpData: SignUpData)
    // fun signIn()
    // fun signOut()
}