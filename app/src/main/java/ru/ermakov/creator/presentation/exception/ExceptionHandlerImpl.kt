package ru.ermakov.creator.presentation.exception

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import ru.ermakov.creator.data.exception.EmailVerificationException
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.EMAIL_COLLISION_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INVALID_USER_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.NETWORK_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.TOO_MANY_REQUESTS_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.UNKNOWN_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.WEAK_PASSWORD_EXCEPTION
import ru.ermakov.creator.data.exception.InvalidUserException

class ExceptionHandlerImpl : ExceptionHandler {
    override fun handleException(exception: Exception): String {
        return when (exception) {
            is FirebaseNetworkException -> {
                NETWORK_EXCEPTION
            }

            is FirebaseTooManyRequestsException -> {
                TOO_MANY_REQUESTS_EXCEPTION
            }

            is FirebaseAuthInvalidUserException -> {
                INVALID_USER_EXCEPTION
            }

            is FirebaseAuthWeakPasswordException -> {
                WEAK_PASSWORD_EXCEPTION
            }

            is FirebaseAuthInvalidCredentialsException -> {
                exception.message.toString().dropLastWhile { lastChar ->
                    lastChar == '.'
                }
            }

            is FirebaseAuthUserCollisionException -> {
                EMAIL_COLLISION_EXCEPTION
            }

            is EmailVerificationException -> {
                exception.message
            }

            is InvalidUserException -> {
                exception.message
            }

            else -> {
                UNKNOWN_EXCEPTION
            }
        }
    }
}