package ru.ermakov.creator.domain.repository

import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.util.Resource

interface AuthRepository {
    suspend fun signIn(signInData: SignInData): Resource<SignInData>
    suspend fun signedIn(): Resource<SignInData>
    suspend fun signUp(signUpData: SignUpData): Resource<SignUpData>

    // fun signOut()
    // fun validateEmail(signUpData: SignUpData)
}