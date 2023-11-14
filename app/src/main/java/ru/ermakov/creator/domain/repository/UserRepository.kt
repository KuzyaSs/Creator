package ru.ermakov.creator.domain.repository

import ru.ermakov.creator.domain.model.AuthUser
import ru.ermakov.creator.domain.model.User

interface UserRepository {
    suspend fun getUserById(userId: String): User
    suspend fun insertUser(authUser: AuthUser)
    suspend fun updateUser(user: User)
}