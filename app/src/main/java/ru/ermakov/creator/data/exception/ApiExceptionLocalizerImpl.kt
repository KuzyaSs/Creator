package ru.ermakov.creator.data.exception

import com.google.gson.Gson
import retrofit2.Response
import ru.ermakov.creator.domain.exception.ErrorConstants

class ApiExceptionLocalizerImpl(private val gson: Gson) : ApiExceptionLocalizer {
    override fun localizeApiException(response: Response<*>): Exception {
        val apiExceptionBody = gson.fromJson(
            response.errorBody()?.string(), ApiExceptionBody::class.java
        )

        return when (apiExceptionBody.error) {
            ErrorConstants.USERNAME_IN_USE_EXCEPTION -> {
                UsernameInUseException()
            }

            ErrorConstants.USER_NOT_FOUND_EXCEPTION -> {
                UserNotFoundException()
            }

            else -> {
                Exception()
            }
        }
    }
}