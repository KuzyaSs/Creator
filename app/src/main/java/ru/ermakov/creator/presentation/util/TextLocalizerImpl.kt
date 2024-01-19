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
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INSUFFICIENT_FUNDS_IN_ACCOUNT_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INSUFFICIENT_FUNDS_IN_GOAL_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INVALID_CARD_NUMBER_LENGTH_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INVALID_CVV_LENGTH_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INVALID_SUBSCRIPTION_PRICE_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INVALID_TIP_AMOUNT_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.INVALID_VALIDITY_LENGTH_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.SUBSCRIPTION_NOT_FOUND_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.TRANSACTION_NOT_FOUND_EXCEPTION
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

            "Top-up" -> {
                context.getString(R.string.top_up)
            }

            "Withdrawal" -> {
                context.getString(R.string.withdrawal)
            }

            "Credit goal closure" -> {
                context.getString(R.string.credit_goal_closure)
            }

            "Subscription purchase" -> {
                context.getString(R.string.subscription_purchase)
            }

            "Transfer to a user" -> {
                context.getString(R.string.transfer_to_user)
            }

            "Transfer to a credit goal" -> {
                context.getString(R.string.transfer_to_credit_goal)
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

            INVALID_SUBSCRIPTION_PRICE_EXCEPTION -> {
                context.getString(R.string.invalid_subscription_price_exception)
            }

            INVALID_CARD_NUMBER_LENGTH_EXCEPTION -> {
                context.getString(R.string.invalid_card_number_length_exception)
            }

            INVALID_VALIDITY_LENGTH_EXCEPTION -> {
                context.getString(R.string.invalid_validity_length_exception)
            }

            INVALID_CVV_LENGTH_EXCEPTION -> {
                context.getString(R.string.invalid_cvv_length_exception)
            }

            INVALID_TIP_AMOUNT_EXCEPTION -> {
                context.getString(R.string.invalid_tip_amount_exception)
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

            INSUFFICIENT_FUNDS_IN_ACCOUNT_EXCEPTION -> {
                context.getString(R.string.insufficient_funds_in_account_exception)
            }

            INSUFFICIENT_FUNDS_IN_GOAL_EXCEPTION -> {
                context.getString(R.string.insufficient_funds_in_goal_exception)
            }

            TRANSACTION_NOT_FOUND_EXCEPTION -> {
                context.getString(R.string.transaction_not_found_exception)
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