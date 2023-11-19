package ru.ermakov.creator.domain.repository

import ru.ermakov.creator.domain.model.AuthUser
import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.domain.model.SignUpData

interface AuthRepository {
    suspend fun signIn(signInData: SignInData)

    suspend fun signedIn()

    suspend fun signUp(signUpData: SignUpData): AuthUser

    fun signOut()

    suspend fun resetPassword(email: String)

    suspend fun getCurrentUserId(): String
}