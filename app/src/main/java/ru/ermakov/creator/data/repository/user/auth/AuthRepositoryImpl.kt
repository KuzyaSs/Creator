package ru.ermakov.creator.data.repository.user.auth

import kotlinx.coroutines.delay
import ru.ermakov.creator.data.dataSource.local.AuthLocalDataSource
import ru.ermakov.creator.data.exception.ExceptionHandler
import ru.ermakov.creator.data.service.AuthService
import ru.ermakov.creator.data.toUser
import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.data.repository.user.UserRepository
import ru.ermakov.creator.util.Constant.Companion.SPLASH_SCREEN_DELAY
import ru.ermakov.creator.util.Resource

class AuthRepositoryImpl(
    private val userRepository: UserRepository,
    private val authLocalDataSource: AuthLocalDataSource,
    private val authService: AuthService,
    private val exceptionHandler: ExceptionHandler
) : AuthRepository {
    override suspend fun signIn(signInData: SignInData): Resource<SignInData> {
        return try {
            authService.signIn(signInData = signInData)
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
            authService.signedIn(signInData = signInData)
            Resource.Success(data = signInData)
        } catch (exception: Exception) {
            val message = exceptionHandler.handleException(exception = exception)
            Resource.Error(data = null, message = message)
        }
    }

    override suspend fun signUp(signUpData: SignUpData): Resource<SignUpData> {
        return try {
            val authUser = authService.signUp(signUpData = signUpData)
            userRepository.insertUser(authUser.toUser())
            Resource.Success(data = signUpData)
        } catch (exception: Exception) {
            val message = exceptionHandler.handleException(exception = exception)
            Resource.Error(data = null, message = message)
        }
    }

    override suspend fun resetPassword(email: String): Resource<String> {
        return try {
            authService.sendPasswordResetEmail(email)
            Resource.Success(data = email)
        } catch (exception: Exception) {
            val message = exceptionHandler.handleException(exception = exception)
            Resource.Error(data = null, message = message)
        }
    }
}