package ru.ermakov.creator.data.repository.user

import kotlinx.coroutines.flow.Flow
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.util.Resource

interface UserRepository {
    suspend fun insertUser(user: User): Resource<User>
    suspend fun getUser(id: String): Flow<Resource<User>>
    suspend fun getCurrentUser(): Flow<Resource<User>>
    suspend fun updateUser(user: User): Resource<User>
    fun detachUserListener()
}