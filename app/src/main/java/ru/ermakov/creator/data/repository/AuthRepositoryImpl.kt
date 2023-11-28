package ru.ermakov.creator.data.repository

import kotlinx.coroutines.delay
import ru.ermakov.creator.data.local.dataSource.AuthLocalDataSource
import ru.ermakov.creator.data.remote.dataSource.AuthRemoteDataSource
import ru.ermakov.creator.domain.model.AuthUser
import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.domain.repository.AuthRepository

private const val SPLASH_SCREEN_DELAY = 1500L

class AuthRepositoryImpl(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {
    override suspend fun signIn(signInData: SignInData) {
        authRemoteDataSource.signIn(signInData = signInData)
        authLocalDataSource.saveSignInData(signInData = signInData)
    }

    override suspend fun signedIn() {
        val signInData = authLocalDataSource.getSignInData()
        delay(SPLASH_SCREEN_DELAY)
        authRemoteDataSource.signedIn(signInData = signInData)
    }

    override suspend fun signUp(signUpData: SignUpData): AuthUser {
        return authRemoteDataSource.signUp(signUpData = signUpData)
    }

    override fun signOut() {
        authLocalDataSource.clearSignInData()
        authRemoteDataSource.signOut()
    }

    override suspend fun resetPassword(email: String) {
        authRemoteDataSource.sendPasswordResetEmail(email)
    }

    override suspend fun changePassword(currentPassword: String, newPassword: String) {
        authRemoteDataSource.changePassword(
            currentPassword = currentPassword,
            newPassword = newPassword
        )
        val updatedSignInDate = authLocalDataSource.getSignInData().copy(password = newPassword)
        authLocalDataSource.saveSignInData(signInData = updatedSignInDate)
    }

    override suspend fun getCurrentUserId(): String {
        return authRemoteDataSource.getCurrentUser().uid
    }
}