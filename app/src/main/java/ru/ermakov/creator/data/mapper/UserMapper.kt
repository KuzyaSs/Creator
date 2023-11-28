package ru.ermakov.creator.data.mapper

import com.google.firebase.auth.FirebaseUser
import ru.ermakov.creator.data.local.entity.UserEntity
import ru.ermakov.creator.data.remote.model.RemoteUser
import ru.ermakov.creator.domain.model.AuthUser
import ru.ermakov.creator.domain.model.User
import java.time.Instant
import java.time.LocalDate
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

fun User.toRemoteUser(): RemoteUser {
    return RemoteUser(
        id = id,
        username = username,
        email = email,
        bio = bio,
        profileAvatarUrl = profileAvatarUrl,
        profileBackgroundUrl = profileBackgroundUrl,
        isModerator = isModerator,
        registrationDate = registrationDate.toString()
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
        isModerator = false, // Fix later.
        registrationDate = Instant
            .ofEpochMilli(registrationDate)
            .atZone(ZoneOffset.UTC)
            .toLocalDate()
    )
}

fun RemoteUser.toUser(): User {
    return User(
        id = id,
        username = username,
        email = email,
        bio = bio,
        profileAvatarUrl = profileAvatarUrl,
        profileBackgroundUrl = profileBackgroundUrl,
        isModerator = isModerator,
        registrationDate = LocalDate.parse(registrationDate)
    )
}

fun FirebaseUser.toAuthUser(): AuthUser {
    return AuthUser(
        id = uid,
        username = email.toString(),
        email = email.toString()
    )
}