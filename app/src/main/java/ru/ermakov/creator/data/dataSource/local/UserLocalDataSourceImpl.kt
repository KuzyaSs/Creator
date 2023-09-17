package ru.ermakov.creator.data.dataSource.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.ermakov.creator.data.dataSource.local.database.dao.UserDao
import ru.ermakov.creator.data.repository.user.UserLocalDataSource
import ru.ermakov.creator.data.toUser
import ru.ermakov.creator.data.toUserEntity
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.util.Constant.Companion.INVALID_USER_EXCEPTION
import ru.ermakov.creator.util.Resource

class UserLocalDataSourceImpl(private val userDao: UserDao) : UserLocalDataSource {
    override suspend fun insert(user: User) {
        userDao.insert(user.toUserEntity())
    }

    override suspend fun get(id: String): Flow<Resource<User>> {
        return userDao.getUserById(id = id).map { userEntity ->
            if (userEntity != null) {
                Resource.Success(userEntity.toUser())
            } else {
                Resource.Error(data = null, message = INVALID_USER_EXCEPTION)
            }
        }
    }

    override suspend fun update(user: User) {
        userDao.update(user.toUserEntity())
    }
}