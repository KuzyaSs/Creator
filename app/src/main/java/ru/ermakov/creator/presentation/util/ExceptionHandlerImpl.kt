package ru.ermakov.creator.presentation.util

import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import ru.ermakov.creator.data.exception.EmailVerificationException
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.EMAIL_COLLISION_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.UNKNOWN_EMAIL_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.NETWORK_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.TOO_MANY_REQUESTS_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.UNKNOWN_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.WEAK_PASSWORD_EXCEPTION
import ru.ermakov.creator.data.exception.InvalidUserException
import ru.ermakov.creator.data.exception.UserNotFoundException
import ru.ermakov.creator.data.exception.UsernameInUseException
import ru.ermakov.creator.domain.exception.EmptyDataException
import ru.ermakov.creator.domain.exception.PasswordMismatchException
import ru.ermakov.creator.domain.exception.ShortUsernameException

class ExceptionHandlerImpl : ExceptionHandler {
    override fun handleException(exception: Throwable): String {
        Log.d("MY_TAG", "EXCEPTION: ${exception.message}")
        return when (exception) {
            is EmptyDataException -> {
                exception.message
            }

            is ShortUsernameException -> {
                exception.message
            }

            is UsernameInUseException -> {
                exception.message
            }

            is UserNotFoundException -> {
                exception.message
            }

            is PasswordMismatchException -> {
                exception.message
            }

            is EmailVerificationException -> {
                exception.message
            }

            is InvalidUserException -> {
                exception.message
            }

            is FirebaseNetworkException -> {
                NETWORK_EXCEPTION
            }

            is FirebaseTooManyRequestsException -> {
                TOO_MANY_REQUESTS_EXCEPTION
            }

            is FirebaseAuthInvalidUserException -> {
                UNKNOWN_EMAIL_EXCEPTION
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

            else -> {
                UNKNOWN_EXCEPTION
            }
        }
    }
}