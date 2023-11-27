package ru.ermakov.creator.data.remote.dataSource

import android.util.Log
import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.mapper.toRemoteUser
import ru.ermakov.creator.data.mapper.toUser
import ru.ermakov.creator.data.remote.api.UserApi
import ru.ermakov.creator.domain.model.AuthUser
import ru.ermakov.creator.domain.model.User

class UserRemoteDataSourceImpl(
    private val userApi: UserApi,
    private val apiExceptionLocalizer: ApiExceptionLocalizer
) :
    UserRemoteDataSource {
    override suspend fun getUserById(userId: String): User {
        val remoteUserResponse = userApi.getUserById(userId = userId)
        if (remoteUserResponse.isSuccessful) {
            remoteUserResponse.body()?.let { remoteUser ->
                return remoteUser.toUser()
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remoteUserResponse)
    }

    override suspend fun insertUser(authUser: AuthUser) {
        val response = userApi.insertUser(authUser = authUser)
        if (response.isSuccessful) {
            Log.d("MY_TAG", "INSERT_USER (SUCCESS): $authUser")
            return
        }
        Log.d("MY_TAG", "INSERT_USER (ERROR): ${response.body()}")
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun updateUser(user: User) {
        val response = userApi.updateUser(user = user.toRemoteUser())
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }
}