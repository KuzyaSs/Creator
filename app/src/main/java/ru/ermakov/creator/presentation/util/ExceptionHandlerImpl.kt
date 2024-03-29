package ru.ermakov.creator.presentation.util

import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.CancellationException
import ru.ermakov.creator.data.exception.CommentNotFoundException
import ru.ermakov.creator.data.exception.CreditGoalNotFoundException
import ru.ermakov.creator.data.exception.DuplicateSubscriptionTitleException
import ru.ermakov.creator.data.exception.DuplicateTagNameException
import ru.ermakov.creator.data.exception.DuplicateUserSubscriptionException
import ru.ermakov.creator.data.exception.EmailVerificationException
import ru.ermakov.creator.data.exception.FollowNotFoundException
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.EMAIL_COLLISION_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.UNKNOWN_EMAIL_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.NETWORK_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.TOO_MANY_REQUESTS_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.UNKNOWN_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.WEAK_PASSWORD_EXCEPTION
import ru.ermakov.creator.data.exception.InvalidUserException
import ru.ermakov.creator.data.exception.UserNotFoundException
import ru.ermakov.creator.data.exception.DuplicateUsernameException
import ru.ermakov.creator.data.exception.InsufficientFundsInAccountException
import ru.ermakov.creator.data.exception.InsufficientFundsInGoalException
import ru.ermakov.creator.data.exception.PostNotFoundException
import ru.ermakov.creator.data.exception.SubscriptionNotFoundException
import ru.ermakov.creator.data.exception.TransactionNotFoundException
import ru.ermakov.creator.domain.exception.EmptyDataException
import ru.ermakov.creator.domain.exception.InvalidCardNumberLengthException
import ru.ermakov.creator.domain.exception.InvalidCreditGoalTargetBalanceException
import ru.ermakov.creator.domain.exception.InvalidCvvLengthException
import ru.ermakov.creator.domain.exception.InvalidSubscriptionPriceException
import ru.ermakov.creator.domain.exception.InvalidTransactionAmountException
import ru.ermakov.creator.domain.exception.InvalidValidityLengthException
import ru.ermakov.creator.domain.exception.PasswordMismatchException
import ru.ermakov.creator.domain.exception.ShortUsernameException

class ExceptionHandlerImpl : ExceptionHandler {
    override fun handleException(exception: Throwable): String {
        Log.d("MY_TAG", "ExceptionHandler: ${exception.javaClass} / ${exception.message}")
        return when (exception) {
            is EmptyDataException -> {
                exception.message
            }

            is ShortUsernameException -> {
                exception.message
            }

            is DuplicateUsernameException -> {
                exception.message
            }

            is UserNotFoundException -> {
                exception.message
            }

            is FollowNotFoundException -> {
                exception.message
            }

            is SubscriptionNotFoundException -> {
                exception.message
            }

            is CreditGoalNotFoundException -> {
                exception.message
            }

            is InvalidSubscriptionPriceException -> {
                exception.message
            }

            is InvalidCreditGoalTargetBalanceException -> {
                exception.message
            }

            is InvalidTransactionAmountException -> {
                exception.message
            }

            is InvalidCardNumberLengthException -> {
                exception.message
            }

            is InvalidValidityLengthException -> {
                exception.message
            }

            is InvalidCvvLengthException -> {
                exception.message
            }

            is DuplicateSubscriptionTitleException -> {
                exception.message
            }

            is DuplicateUserSubscriptionException -> {
                exception.message
            }

            is DuplicateTagNameException -> {
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

            is InsufficientFundsInAccountException -> {
                exception.message
            }

            is InsufficientFundsInGoalException -> {
                exception.message
            }

            is TransactionNotFoundException -> {
                exception.message
            }

            is CommentNotFoundException -> {
                exception.message
            }

            is PostNotFoundException -> {
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

            is CancellationException -> {
                ""
            }

            else -> {
                UNKNOWN_EXCEPTION
            }
        }
    }
}