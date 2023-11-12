package ru.ermakov.creator.data.repository.user.auth

import kotlinx.coroutines.delay
import ru.ermakov.creator.data.exception.ExceptionHandler
import ru.ermakov.creator.data.local.dataSource.AuthLocalDataSource
import ru.ermakov.creator.data.remote.dataSource.AuthRemoteDataSource
import ru.ermakov.creator.data.remote.model.AuthUserRemote
import ru.ermakov.creator.data.repository.user.UserRepository
import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.util.Constant.Companion.SPLASH_SCREEN_DELAY
import ru.ermakov.creator.util.Resource

class AuthRepositoryImpl(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val exceptionHandler: ExceptionHandler
) : AuthRepository {
    override suspend fun signIn(signInData: SignInData): Resource<SignInData> {
        return try {
            authRemoteDataSource.signIn(signInData = signInData)
            authLocalDataSource.save(signInData = signInData)
            Resource.Success(data = signInData)
        } catch (exception: Exception) {
            val message = exceptionHandler.handleException(exception = exception)
            Resource.Error(data = null, message = message)
        }
    }

    override suspend fun signedIn(): Resource<SignInData> {
        val signInData = authLocalDataSource.get()
        delay(SPLASH_SCREEN_DELAY)
        return try {
            authRemoteDataSource.signedIn(signInData = signInData)
            Resource.Success(data = signInData)
        } catch (exception: Exception) {
            val message = exceptionHandler.handleException(exception = exception)
            Resource.Error(data = null, message = message)
        }
    }

    override suspend fun signUp(signUpData: SignUpData): Resource<AuthUserRemote> {
        return try {
            val authUserRemote = authRemoteDataSource.signUp(signUpData = signUpData)
            Resource.Success(data = authUserRemote)
        } catch (exception: Exception) {
            val message = exceptionHandler.handleException(exception = exception)
            Resource.Error(data = null, message = message)
        }
    }

    override suspend fun resetPassword(email: String): Resource<String> {
        return try {
            authRemoteDataSource.sendPasswordResetEmail(email)
            Resource.Success(data = email)
        } catch (exception: Exception) {
            val message = exceptionHandler.handleException(exception = exception)
            Resource.Error(data = null, message = message)
        }
    }
}