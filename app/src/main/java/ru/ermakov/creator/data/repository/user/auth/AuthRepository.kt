package ru.ermakov.creator.data.repository.user.auth

import ru.ermakov.creator.data.remote.model.AuthUserRemote
import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.util.Resource

interface AuthRepository {
    suspend fun signIn(signInData: SignInData): Resource<SignInData>
    suspend fun signedIn(): Resource<SignInData>
    suspend fun signUp(signUpData: SignUpData): Resource<AuthUserRemote>

    suspend fun resetPassword(email: String): Resource<String>
    // fun signOut()
}