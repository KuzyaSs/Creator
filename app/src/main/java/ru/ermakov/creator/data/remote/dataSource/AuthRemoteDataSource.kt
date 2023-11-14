package ru.ermakov.creator.data.remote.dataSource

import com.google.firebase.auth.FirebaseUser
import ru.ermakov.creator.domain.model.AuthUser
import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.domain.model.SignUpData

interface AuthRemoteDataSource {
    suspend fun signIn(signInData: SignInData)
    suspend fun signedIn(signInData: SignInData)
    suspend fun signUp(signUpData: SignUpData): AuthUser
    fun signOut()
    fun getCurrentUser(): FirebaseUser
    suspend fun sendPasswordResetEmail(email: String)
}