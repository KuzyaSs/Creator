package ru.ermakov.creator.data.repository

import ru.ermakov.creator.data.remote.dataSource.UserRemoteDataSource
import ru.ermakov.creator.domain.model.AuthUser
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.domain.repository.UserRepository

class UserRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource) : UserRepository {
    override suspend fun getUserById(userId: String): User {
        return userRemoteDataSource.getUserById(userId = userId)
    }

    override suspend fun insertUser(authUser: AuthUser) {
        userRemoteDataSource.insertUser(authUser = authUser)
    }

    override suspend fun updateUser(user: User) {
        userRemoteDataSource.updateUser(user = user)
    }
}