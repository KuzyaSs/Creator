package ru.ermakov.creator.data.dataSource.remote

import ru.ermakov.creator.domain.model.User

interface UserRemoteDataSource {
    suspend fun insert(user: User)
    suspend fun update(user: User)
    fun attachListenerToUserById(id: String, update: (User) -> Unit)
    fun detachListener()
}