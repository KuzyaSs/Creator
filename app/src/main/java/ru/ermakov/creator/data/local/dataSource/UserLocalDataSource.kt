package ru.ermakov.creator.data.local.dataSource

import kotlinx.coroutines.flow.Flow
import ru.ermakov.creator.domain.model.User

interface UserLocalDataSource {
    suspend fun insert(user: User)
    suspend fun get(id: String): Flow<User>
    suspend fun update(user: User)
}