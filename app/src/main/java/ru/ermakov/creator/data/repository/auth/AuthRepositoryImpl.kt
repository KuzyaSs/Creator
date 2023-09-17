package ru.ermakov.creator.data.repository.auth

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.delay
import ru.ermakov.creator.data.service.AuthService
import ru.ermakov.creator.data.dataSource.local.SingInLocalDataSource
import ru.ermakov.creator.data.toUser
import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.domain.repository.UserRepository
import ru.ermakov.creator.util.Constant.Companion.EMAIL_COLLISION_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.EMAIL_FORMAT_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.INVALID_USER_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.NETWORK_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.SPLASH_SCREEN_DELAY
import ru.ermakov.creator.util.Constant.Companion.TOO_MANY_REQUESTS_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.UNKNOWN_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.WEAK_PASSWORD_EXCEPTION
import ru.ermakov.creator.util.Resource
import ru.ermakov.creator.util.exception.EmailVerificationException
import ru.ermakov.creator.util.exception.InvalidUserException

class AuthRepositoryImpl(
    private val userRepository: UserRepository,
    private val singInLocalDataSource: SingInLocalDataSource,
    private val authService: AuthService
) : AuthRepository {
    override suspend fun signIn(signInData: SignInData): Resource<SignInData> {
        return try {
            authService.signIn(signInData = signInData)
            singInLocalDataSource.save(signInData = signInData)
            Resource.Success(data = signInData)
        } catch (exception: Exception) {
            when (exception) {
                is FirebaseNetworkException -> {
                    Resource.Error(data = null, message = NETWORK_EXCEPTION)
                }

                is FirebaseTooManyRequestsException -> {
                    Resource.Error(data = null, message = TOO_MANY_REQUESTS_EXCEPTION)
                }

                is FirebaseAuthInvalidUserException -> {
                    Resource.Error(data = null, message = INVALID_USER_EXCEPTION)
                }

                is FirebaseAuthInvalidCredentialsException -> {
                    Resource.Error(
                        data = null,
                        message = exception.message.toString().dropLastWhile { lastChar ->
                            lastChar == '.'
                        })
                }

                is EmailVerificationException -> {
                    Resource.Error(data = null, message = exception.message)
                }

                is InvalidUserException -> {
                    Resource.Error(data = null, message = exception.message)
                }

                else -> {
                    Resource.Error(data = null, message = UNKNOWN_EXCEPTION)
                }
            }
        }
    }

    override suspend fun signedIn(): Resource<SignInData> {
        val signInData = singInLocalDataSource.get()
        delay(SPLASH_SCREEN_DELAY)
        return try {
            authService.signedIn(signInData = signInData)
            Resource.Success(data = signInData)
        } catch (exception: Exception) {
            when (exception) {
                is FirebaseNetworkException -> {
                    Resource.Error(data = signInData, message = NETWORK_EXCEPTION)
                }

                else -> {
                    Resource.Error(data = null, message = UNKNOWN_EXCEPTION)
                }
            }
        }
    }

    override suspend fun signUp(signUpData: SignUpData): Resource<SignUpData> {
        return try {
            val authUser = authService.signUp(signUpData = signUpData)
            userRepository.insertUser(authUser.toUser())
            Resource.Success(data = signUpData)
        } catch (exception: Exception) {
            when (exception) {
                is FirebaseNetworkException -> {
                    Resource.Error(data = null, message = NETWORK_EXCEPTION)
                }

                is FirebaseAuthWeakPasswordException -> {
                    Resource.Error(data = null, message = WEAK_PASSWORD_EXCEPTION)
                }

                is FirebaseAuthInvalidCredentialsException -> {
                    Resource.Error(data = null, message = EMAIL_FORMAT_EXCEPTION)
                }

                is FirebaseAuthUserCollisionException -> {
                    Resource.Error(data = null, message = EMAIL_COLLISION_EXCEPTION)
                }

                is InvalidUserException -> {
                    Resource.Error(data = null, message = exception.message)
                }

                else -> {
                    Resource.Error(data = null, message = UNKNOWN_EXCEPTION)
                }
            }
        }
    }

    override suspend fun resetPassword(email: String): Resource<String> {
        return try {
            authService.sendPasswordResetEmail(email)
            Resource.Success(data = email)

        } catch (exception: Exception) {
            when (exception) {
                is FirebaseNetworkException -> {
                    Resource.Error(data = null, message = NETWORK_EXCEPTION)
                }

                is FirebaseAuthInvalidCredentialsException -> {
                    Resource.Error(data = null, message = EMAIL_FORMAT_EXCEPTION)
                }

                is FirebaseAuthInvalidUserException -> {
                    Resource.Error(data = null, message = INVALID_USER_EXCEPTION)
                }

                else -> {
                    Resource.Error(data = null, message = UNKNOWN_EXCEPTION)
                }
            }
        }
    }
}