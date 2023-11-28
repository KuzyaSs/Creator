package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.domain.model.AuthUser
import ru.ermakov.creator.domain.model.User

interface UserRemoteDataSource {
    suspend fun getUserById(userId: String): User

    suspend fun insertUser(authUser: AuthUser)

    suspend fun updateUser(user: User)
}