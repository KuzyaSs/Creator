package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.data.remote.model.AuthUserRemote
import ru.ermakov.creator.domain.model.User

interface UserRemoteDataSource {
    suspend fun insertUser(authUserRemote: AuthUserRemote)
    suspend fun updateUser(user: User)
}