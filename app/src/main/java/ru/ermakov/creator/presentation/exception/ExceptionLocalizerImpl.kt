package ru.ermakov.creator.presentation.exception

import android.content.Context
import ru.ermakov.creator.R
import ru.ermakov.creator.util.Constant

class ExceptionLocalizerImpl(private val context: Context) : ExceptionLocalizer {
    override fun localizeException(message: String): String {
        return when (message) {
            Constant.NETWORK_EXCEPTION -> {
                context.getString(R.string.network_exception)
            }

            Constant.EMPTY_DATA_EXCEPTION -> {
                context.getString(R.string.empty_data_exception)
            }

            Constant.EMAIL_FORMAT_EXCEPTION -> {
                context.getString(R.string.email_format_exception)
            }

            Constant.EMAIL_VERIFICATION_EXCEPTION -> {
                context.getString(R.string.email_verification_exception)
            }

            Constant.EMAIL_COLLISION_EXCEPTION -> {
                context.getString(R.string.email_collision_exception)
            }

            Constant.INVALID_PASSWORD_EXCEPTION -> {
                context.getString(R.string.invalid_password_exception)
            }

            Constant.PASSWORDS_DON_T_MATCH_EXCEPTION -> {
                context.getString(R.string.passwords_don_t_match_exception)
            }

            Constant.WEAK_PASSWORD_EXCEPTION -> {
                context.getString(R.string.weak_password_exception)

            }

            Constant.INVALID_USER_EXCEPTION -> {
                context.getString(R.string.invalid_user_exception)
            }

            Constant.TOO_MANY_REQUESTS_EXCEPTION -> {
                context.getString(R.string.too_many_requests_exception)
            }

            else -> {
                context.getString(R.string.unknown_exception)
            }
        }
    }
}