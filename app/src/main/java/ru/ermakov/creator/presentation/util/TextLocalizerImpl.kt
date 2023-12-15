package ru.ermakov.creator.presentation.util

import android.content.Context
import ru.ermakov.creator.R
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.DUPLICATE_SUBSCRIPTION_TITLE_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.EMAIL_COLLISION_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.EMAIL_FORMAT_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.EMAIL_VERIFICATION_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.EMPTY_DATA_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.FOLLOW_NOT_FOUND_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INVALID_PASSWORD_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.UNKNOWN_EMAIL_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.NETWORK_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.PASSWORD_MISMATCH_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.SHORT_USERNAME_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.TOO_MANY_REQUESTS_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.DUPLICATE_USERNAME_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.DUPLICATE_USER_SUBSCRIPTION_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.SUBSCRIPTION_NOT_FOUND_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.USER_NOT_FOUND_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.WEAK_PASSWORD_EXCEPTION

class TextLocalizerImpl(private val context: Context) : TextLocalizer {
    override fun localizeText(text: String): String {
        return when (text) {
            "Science" -> {
                context.getString(R.string.science)
            }

            "Technology" -> {
                context.getString(R.string.technology)
            }

            "Economics" -> {
                context.getString(R.string.economics)
            }

            "Politics" -> {
                context.getString(R.string.politics)
            }

            "Health" -> {
                context.getString(R.string.health)
            }

            "Sport" -> {
                context.getString(R.string.sport)
            }

            "Culture" -> {
                context.getString(R.string.culture)
            }

            "Environment" -> {
                context.getString(R.string.environment)
            }

            "Travel" -> {
                context.getString(R.string.travel)
            }

            "Entertainment" -> {
                context.getString(R.string.entertainment)
            }

            NETWORK_EXCEPTION -> {
                context.getString(R.string.network_exception)
            }

            SHORT_USERNAME_EXCEPTION -> {
                context.getString(R.string.short_username_exception)
            }

            DUPLICATE_USERNAME_EXCEPTION -> {
                context.getString(R.string.username_in_use_exception)
            }

            USER_NOT_FOUND_EXCEPTION -> {
                context.getString(R.string.user_not_found_exception)
            }

            SUBSCRIPTION_NOT_FOUND_EXCEPTION -> {
                context.getString(R.string.subscription_not_found_exception)
            }

            DUPLICATE_SUBSCRIPTION_TITLE_EXCEPTION -> {
                context.getString(R.string.duplicate_subscription_title_exception)
            }

            DUPLICATE_USER_SUBSCRIPTION_EXCEPTION -> {
                context.getString(R.string.duplicate_user_subscription_exception)
            }

            FOLLOW_NOT_FOUND_EXCEPTION -> {
                context.getString(R.string.user_not_found_exception)
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

            UNKNOWN_EMAIL_EXCEPTION -> {
                context.getString(R.string.unknown_email_exception)
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