package ru.ermakov.creator.data.exception

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import ru.ermakov.creator.util.Constant.Companion.EMAIL_COLLISION_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.INVALID_USER_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.NETWORK_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.TOO_MANY_REQUESTS_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.UNKNOWN_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.WEAK_PASSWORD_EXCEPTION

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