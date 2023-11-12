package ru.ermakov.creator.data.repository.user

import kotlinx.coroutines.flow.Flow
import ru.ermakov.creator.data.remote.model.AuthUserRemote
import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.util.Resource

interface UserRepository {
    suspend fun getUser(id: String): Resource<User>
    suspend fun getCurrentUser(): Resource<User>
    suspend fun insertUser(authUserRemote: AuthUserRemote): Resource<SignUpData>
    suspend fun updateUser(user: User): Resource<User>
}