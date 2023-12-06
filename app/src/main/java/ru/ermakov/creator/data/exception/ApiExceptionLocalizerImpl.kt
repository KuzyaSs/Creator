package ru.ermakov.creator.data.exception

import android.util.Log
import com.google.gson.Gson
import retrofit2.Response
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.FOLLOW_NOT_FOUND_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.USERNAME_IN_USE_EXCEPTION
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.USER_NOT_FOUND_EXCEPTION

class ApiExceptionLocalizerImpl(private val gson: Gson) : ApiExceptionLocalizer {
    override fun localizeApiException(response: Response<*>): Exception {
        val apiExceptionBody = gson.fromJson(
            response.errorBody()?.string(), ApiExceptionBody::class.java
        )
        Log.d("MY_TAG", "ApiExceptionLocalizer ERROR: $apiExceptionBody")
        return when (apiExceptionBody.error) {
            USERNAME_IN_USE_EXCEPTION -> {
                UsernameInUseException()
            }

            USER_NOT_FOUND_EXCEPTION -> {
                UserNotFoundException()
            }

            FOLLOW_NOT_FOUND_EXCEPTION -> {
                FollowNotFoundException()
            }

            else -> {
                Exception()
            }
        }
    }
}