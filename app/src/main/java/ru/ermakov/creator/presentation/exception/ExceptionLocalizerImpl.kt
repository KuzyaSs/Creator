package ru.ermakov.creator.presentation.exception

import android.content.Context
import ru.ermakov.creator.R
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.EMAIL_COLLISION_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.EMAIL_FORMAT_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.EMAIL_VERIFICATION_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.EMPTY_DATA_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INVALID_PASSWORD_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INVALID_USER_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.NETWORK_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.PASSWORD_MISMATCH_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.TOO_MANY_REQUESTS_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.WEAK_PASSWORD_EXCEPTION

class ExceptionLocalizerImpl(private val context: Context) : ExceptionLocalizer {
    override fun localizeException(errorMessage: String): String {
        return when (errorMessage) {
            NETWORK_EXCEPTION -> {
                context.getString(R.string.network_exception)
            }

            EMPTY_DATA_EXCEPTION -> {
                context.getString(R.string.empty_data_exception)
            }

            EMAIL_FORMAT_EXCEPTION -> {
                context.getString(R.string.email_format_exception)
            }

            EMAIL_VERIFICATION_EXCEPTION -> {
                context.getString(R.string.email_verification_exception)
            }

            EMAIL_COLLISION_EXCEPTION -> {
                context.getString(R.string.email_collision_exception)
            }

            INVALID_PASSWORD_EXCEPTION -> {
                context.getString(R.string.invalid_password_exception)
            }

            PASSWORD_MISMATCH_EXCEPTION -> {
                context.getString(R.string.password_mismatch_exception)
            }

            WEAK_PASSWORD_EXCEPTION -> {
                context.getString(R.string.weak_password_exception)

            }

            INVALID_USER_EXCEPTION -> {
                context.getString(R.string.invalid_user_exception)
            }

            TOO_MANY_REQUESTS_EXCEPTION -> {
                context.getString(R.string.too_many_requests_exception)
            }

            else -> {
                context.getString(R.string.unknown_exception)
            }
        }
    }
}