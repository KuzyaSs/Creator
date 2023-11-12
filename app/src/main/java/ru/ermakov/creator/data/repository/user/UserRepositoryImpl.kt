package ru.ermakov.creator.data.repository.user

import com.google.firebase.FirebaseNetworkException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import ru.ermakov.creator.data.exception.InvalidUserException
import ru.ermakov.creator.data.local.dataSource.FileLocalDataSource
import ru.ermakov.creator.data.local.dataSource.UserLocalDataSource
import ru.ermakov.creator.data.remote.dataSource.AuthRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.FileRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.UserRemoteDataSource
import ru.ermakov.creator.data.remote.model.AuthUserRemote
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.util.Constant.Companion.NETWORK_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.UNKNOWN_EXCEPTION
import ru.ermakov.creator.util.Resource

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val fileRemoteDataSource: FileRemoteDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource
) : UserRepository {

    override suspend fun getUser(id: String): Flow<Resource<User>> {
        return try {
            userRemoteDataSource.attachListenerToUserById(id = id) { user ->
                CoroutineScope(Dispatchers.IO).launch {
                    var changedUser = user.copy()
                    if (user.profileAvatar.isNotEmpty()) {
                        fileRemoteDataSource.download(
                            file = fileLocalDataSource.save(path = user.profileAvatar),
                            path = user.profileAvatar
                        )
                        changedUser = changedUser.copy(
                            profileAvatar = fileLocalDataSource.getRootPath() + changedUser.profileAvatar
                        )
                    }
                    if (user.profileBackground.isNotEmpty()) {
                        fileRemoteDataSource.download(
                            file = fileLocalDataSource.save(path = user.profileBackground),
                            path = user.profileBackground
                        )
                        changedUser = changedUser.copy(
                            profileBackground = fileLocalDataSource.getRootPath() + changedUser.profileBackground
                        )
                    }
                    userLocalDataSource.insert(user = changedUser)
                }
            }
            userLocalDataSource.get(id = id)
        } catch (exception: Exception) {
            when (exception) {
                is FirebaseNetworkException -> {
                    flowOf(Resource.Error(data = null, message = NETWORK_EXCEPTION))
                }

                is InvalidUserException -> {
                    flowOf(Resource.Error(data = null, message = exception.message))
                }

                else -> {
                    flowOf(Resource.Error(data = null, message = UNKNOWN_EXCEPTION))
                }
            }
        }
    }

    override suspend fun getCurrentUser(): Resource<User> {
        val id = authRemoteDataSource.getCurrentUser().uid
        return getUser(id = id)
    }

    override suspend fun insertUser(authUserRemote: AuthUserRemote): Resource<AuthUserRemote> {
        return try {
            userRemoteDataSource.insertUser(authUserRemote = authUserRemote)
            Resource.Success(data = authUserRemote)
        } catch (exception: Exception) {
            when (exception) {
                is FirebaseNetworkException -> {
                    Resource.Error(data = user, message = NETWORK_EXCEPTION)
                }

                else -> {
                    Resource.Error(data = user, message = UNKNOWN_EXCEPTION)
                }
            }
        }
    }

    override suspend fun updateUser(user: User): Resource<User> {
        return try {
            userRemoteDataSource.updateUser(user = user)
            Resource.Success(data = user)
        } catch (exception: Exception) {
            when (exception) {
                is FirebaseNetworkException -> {
                    Resource.Error(data = user, message = NETWORK_EXCEPTION)
                }

                else -> {
                    Resource.Error(data = user, message = UNKNOWN_EXCEPTION)
                }
            }
        }
    }
}