package ru.ermakov.creator.data.local.dataSource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.ermakov.creator.data.exception.InvalidUserException
import ru.ermakov.creator.data.local.dao.UserDao
import ru.ermakov.creator.data.mapper.toUser
import ru.ermakov.creator.data.mapper.toUserEntity
import ru.ermakov.creator.domain.model.User

class UserLocalDataSourceImpl(private val userDao: UserDao) : UserLocalDataSource {
    override suspend fun insert(user: User) {
        userDao.insert(user.toUserEntity())
    }

    override suspend fun get(id: String): Flow<User> {
        return userDao.getUserById(id = id).map { userEntity ->
            userEntity?.toUser() ?: throw InvalidUserException()
        }
    }

    override suspend fun update(user: User) {
        userDao.update(user.toUserEntity())
    }
}