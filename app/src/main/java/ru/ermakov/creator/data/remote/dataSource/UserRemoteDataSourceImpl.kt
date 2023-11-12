package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.data.remote.api.UserApi
import ru.ermakov.creator.data.remote.model.AuthUserRemote
import ru.ermakov.creator.domain.model.User

class UserRemoteDataSourceImpl(private val userApi: UserApi) : UserRemoteDataSource {


    override suspend fun insertUser(authUserRemote: AuthUserRemote) {

    }

    override suspend fun updateUser(user: User) {

    }
}