package ru.ermakov.creator.data.remote.dataSource

import android.util.Log
import ru.ermakov.creator.data.remote.api.UserApi
import ru.ermakov.creator.domain.model.AuthUser
import ru.ermakov.creator.domain.model.User

class UserRemoteDataSourceImpl(private val userApi: UserApi) : UserRemoteDataSource {
    override suspend fun getUserById(userId: String): User {
       TODO()
    }

    override suspend fun insertUser(authUser: AuthUser) {
        Log.d("MY_TAG", "INSERT_USER: $authUser")
    }

    override suspend fun updateUser(user: User) {
        Log.d("MY_TAG", "UPDATE_USER: $user")

    }
}