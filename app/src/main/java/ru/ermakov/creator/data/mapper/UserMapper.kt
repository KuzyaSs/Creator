package ru.ermakov.creator.data.mapper

import com.google.firebase.auth.FirebaseUser
import ru.ermakov.creator.data.local.entity.UserEntity
import ru.ermakov.creator.data.remote.model.AuthUserRemote
import ru.ermakov.creator.domain.model.User

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        username = username,
        email = email,
        about = about,
        profileAvatarUrl = profileAvatarUrl,
        profileBackgroundUrl = profileBackgroundUrl,
        registrationDate = registrationDate
    )
}

fun UserEntity.toUser(): User {
    return User(
        id = id,
        username = username,
        email = email,
        about = about,
        profileAvatarUrl = profileAvatarUrl,
        profileBackgroundUrl = profileBackgroundUrl,
        registrationDate = registrationDate
    )
}

fun FirebaseUser.toAuthUserRemote(): AuthUserRemote {
    return AuthUserRemote(
        id = uid,
        username = uid,
        email = email.toString()
    )
}