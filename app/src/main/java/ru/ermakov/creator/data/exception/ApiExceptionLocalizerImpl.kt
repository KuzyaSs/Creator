package ru.ermakov.creator.data.exception

import android.util.Log
import com.google.gson.Gson
import retrofit2.Response
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.DUPLICATE_SUBSCRIPTION_TITLE_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.FOLLOW_NOT_FOUND_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.DUPLICATE_USERNAME_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.DUPLICATE_USER_SUBSCRIPTION_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.SUBSCRIPTION_NOT_FOUND_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.USER_NOT_FOUND_EXCEPTION

class ApiExceptionLocalizerImpl(private val gson: Gson) : ApiExceptionLocalizer {
    override fun localizeApiException(response: Response<*>): Exception {
        val apiExceptionBody = gson.fromJson(
            response.errorBody()?.string(), ApiExceptionBody::class.java
        )
        Log.d("MY_TAG", "ApiExceptionLocalizer ERROR: $apiExceptionBody")
        return when (apiExceptionBody.error) {
            DUPLICATE_USERNAME_EXCEPTION -> {
                DuplicateUsernameException()
            }

            USER_NOT_FOUND_EXCEPTION -> {
                UserNotFoundException()
            }

            FOLLOW_NOT_FOUND_EXCEPTION -> {
                FollowNotFoundException()
            }

            SUBSCRIPTION_NOT_FOUND_EXCEPTION -> {
                SubscriptionNotFoundException()
            }

            DUPLICATE_SUBSCRIPTION_TITLE_EXCEPTION -> {
                DuplicateSubscriptionTitleException()
            }

            DUPLICATE_USER_SUBSCRIPTION_EXCEPTION -> {
                DuplicateUserSubscriptionException()
            }

            else -> {
                Exception()
            }
        }
    }
}