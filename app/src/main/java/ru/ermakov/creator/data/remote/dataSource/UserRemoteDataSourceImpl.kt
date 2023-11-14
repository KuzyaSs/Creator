package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.data.remote.api.UserApi
import ru.ermakov.creator.domain.model.AuthUser
import ru.ermakov.creator.domain.model.User

class UserRemoteDataSourceImpl(private val userApi: UserApi) : UserRemoteDataSource {
    override suspend fun getUserById(userId: String): User {
       TODO()
    }

    override suspend fun insertUser(authUser: AuthUser) {

    }

    override suspend fun updateUser(user: User) {

    }
}