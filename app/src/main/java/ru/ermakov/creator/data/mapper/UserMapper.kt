package ru.ermakov.creator.data.mapper

import com.google.firebase.auth.FirebaseUser
import ru.ermakov.creator.data.local.entity.UserEntity
import ru.ermakov.creator.domain.model.AuthUser
import ru.ermakov.creator.domain.model.User
import java.time.Instant
import java.time.ZoneOffset

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        username = username,
        email = email,
        bio = bio,
        profileAvatarUrl = profileAvatarUrl,
        profileBackgroundUrl = profileBackgroundUrl,
        registrationDate = registrationDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    )
}

fun UserEntity.toUser(): User {
    return User(
        id = id,
        username = username,
        email = email,
        bio = bio,
        profileAvatarUrl = profileAvatarUrl,
        profileBackgroundUrl = profileBackgroundUrl,
        registrationDate = Instant
            .ofEpochMilli(registrationDate)
            .atZone(ZoneOffset.UTC)
            .toLocalDate()
    )
}

fun FirebaseUser.toAuthUser(): AuthUser {
    return AuthUser(
        id = uid,
        username = uid,
        email = email.toString()
    )
}