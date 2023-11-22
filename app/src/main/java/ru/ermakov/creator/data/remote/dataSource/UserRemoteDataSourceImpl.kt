package ru.ermakov.creator.data.remote.dataSource

import android.util.Log
import kotlinx.coroutines.delay
import ru.ermakov.creator.data.remote.api.UserApi
import ru.ermakov.creator.domain.model.AuthUser
import ru.ermakov.creator.domain.model.User
import java.time.LocalDate

class UserRemoteDataSourceImpl(private val userApi: UserApi) : UserRemoteDataSource {
    override suspend fun getUserById(userId: String): User {
        // Temporarily.
        delay(2000)
        return User(
            userId,
            "Kuzya",
            "skepy@,ail.ru",
            "about",
            "https://firebasestorage.googleapis.com/v0/b/creator-26c44.appspot.com/o/myAvatar.png?alt=media&token=e564220f-2ed0-4016-92fd-52f1253072f9",
            "",
            LocalDate.now()
        )
    }

    override suspend fun insertUser(authUser: AuthUser) {
        Log.d("MY_TAG", "INSERT_USER: $authUser")
    }

    override suspend fun updateUser(user: User) {
        delay(2000)
        Log.d("MY_TAG", "UPDATE_USER: $user")
    }
}