package ru.ermakov.creator.data

import com.google.firebase.auth.FirebaseUser
import ru.ermakov.creator.data.dataSource.local.database.model.UserEntity
import ru.ermakov.creator.data.dataSource.remote.model.RemoteUser
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.util.Constant.Companion.EMPTY_STRING

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        email = email,
        username = username,
        profileAvatar = profileAvatar,
        profileBackground = profileBackground,
        about = about
    )
}

fun User.toRemoteUser(): RemoteUser {
    return RemoteUser(
        email = email,
        username = username,
        profileAvatar = profileAvatar,
        profileBackground = profileBackground,
        about = about
    )
}

fun UserEntity.toUser(money: Int = 0): User {
    return User(
        id = id,
        email = email,
        username = username,
        money = money,
        profileAvatar = profileAvatar,
        profileBackground = profileBackground,
        about = about
    )
}

fun RemoteUser.toUser(id: String, money: Int = 0): User {
    return User(
        id = id,
        email = email,
        username = username,
        money = money,
        profileAvatar = profileAvatar,
        profileBackground = profileBackground,
        about = about
    )
}

fun FirebaseUser.toUser(
    money: Int = 0,
    profileAvatar: String = EMPTY_STRING,
    profileBackground: String = EMPTY_STRING,
    about: String = EMPTY_STRING
): User {
    return User(
        id = uid,
        email = email.toString(),
        username = uid,
        money = money,
        profileAvatar = profileAvatar,
        profileBackground = profileBackground,
        about = about
    )
}