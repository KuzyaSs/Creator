package ru.ermakov.creator.data.remote.dataSource

import android.util.Log
import com.google.gson.Gson
import ru.ermakov.creator.data.exception.ApiExceptionBody
import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.mapper.toRemoteUser
import ru.ermakov.creator.data.mapper.toUser
import ru.ermakov.creator.data.remote.api.UserApi
import ru.ermakov.creator.domain.model.AuthUser
import ru.ermakov.creator.domain.model.User

class UserRemoteDataSourceImpl(
    private val userApi: UserApi,
    private val gson: Gson,
    private val apiExceptionLocalizer: ApiExceptionLocalizer
) :
    UserRemoteDataSource {
    override suspend fun getUserById(userId: String): User {
        val remoteUserResponse = userApi.getUserById(userId = userId)
        if (remoteUserResponse.isSuccessful) {
            remoteUserResponse.body()?.let { remoteUser ->
                Log.d("MY_TAG", "GET_USER_BY_ID (SUCCESS): $remoteUser")
                return remoteUser.toUser()
            }
        }

        val apiExceptionBody = gson.fromJson(
            remoteUserResponse.errorBody()?.string(), ApiExceptionBody::class.java
        )
        Log.d("MY_TAG", "GET_USER_BY_ID (ERROR): $apiExceptionBody")
        throw apiExceptionLocalizer.localizeApiException(apiExceptionBody = apiExceptionBody)
    }

    override suspend fun insertUser(authUser: AuthUser) {
        Log.d("MY_TAG", "INSERT_USER: $authUser")
    }

    override suspend fun updateUser(user: User) {
        val response = userApi.updateUser(user = user.toRemoteUser())
        if (response.isSuccessful) {
            Log.d("MY_TAG", "UPDATE_USER (SUCCESS): $user")
            return
        }
        val apiExceptionBody = gson.fromJson(
            response.errorBody()?.string(), ApiExceptionBody::class.java
        )
        Log.d("MY_TAG", "UPDATE_USER (ERROR): $apiExceptionBody")
        throw apiExceptionLocalizer.localizeApiException(apiExceptionBody = apiExceptionBody)
    }
}