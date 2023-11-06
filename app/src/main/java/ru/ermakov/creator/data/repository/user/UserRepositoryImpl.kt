package ru.ermakov.creator.data.repository.user

import com.google.firebase.FirebaseNetworkException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import ru.ermakov.creator.data.dataSource.local.UserLocalDataSource
import ru.ermakov.creator.data.dataSource.remote.UserRemoteDataSource
import ru.ermakov.creator.data.service.AuthService
import ru.ermakov.creator.data.service.LocalStorage
import ru.ermakov.creator.data.service.RemoteStorage
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.util.Constant.Companion.NETWORK_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.UNKNOWN_EXCEPTION
import ru.ermakov.creator.util.Resource
import ru.ermakov.creator.data.exception.InvalidUserException

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val localStorage: LocalStorage,
    private val remoteStorage: RemoteStorage,
    private val authService: AuthService
) : UserRepository {
    override suspend fun insertUser(user: User): Resource<User> {
        return try {
            userRemoteDataSource.insert(user = user)
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

    override suspend fun getUser(id: String): Flow<Resource<User>> {
        return try {
            userRemoteDataSource.attachListenerToUserById(id = id) { user ->
                CoroutineScope(Dispatchers.IO).launch {
                    var changedUser = user.copy()
                    if (user.profileAvatar.isNotEmpty()) {
                        remoteStorage.download(
                            file = localStorage.save(path = user.profileAvatar),
                            path = user.profileAvatar
                        )
                        changedUser = changedUser.copy(
                            profileAvatar = localStorage.getRootPath() + changedUser.profileAvatar
                        )
                    }
                    if (user.profileBackground.isNotEmpty()) {
                        remoteStorage.download(
                            file = localStorage.save(path = user.profileBackground),
                            path = user.profileBackground
                        )
                        changedUser = changedUser.copy(
                            profileBackground = localStorage.getRootPath() + changedUser.profileBackground
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

    override suspend fun getCurrentUser(): Flow<Resource<User>> {
        val id = authService.getCurrentUser().uid
        return getUser(id = id)
    }

    override suspend fun updateUser(user: User): Resource<User> {
        return try {
            userRemoteDataSource.update(user = user)
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

    override fun detachUserListener() {
        userRemoteDataSource.detachListener()
    }
}