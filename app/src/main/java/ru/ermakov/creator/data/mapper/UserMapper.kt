package ru.ermakov.creator.data.mapper

import com.google.firebase.auth.FirebaseUser
import ru.ermakov.creator.data.local.entity.UserEntity
import ru.ermakov.creator.data.remote.model.RemoteUser
import ru.ermakov.creator.domain.model.AuthUser
import ru.ermakov.creator.domain.model.User
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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

fun RemoteUser.toUser(): User {
    return User(
        id = id,
        username = username,
        email = email,
        bio = bio,
        profileAvatarUrl = profileAvatarUrl,
        profileBackgroundUrl = profileBackgroundUrl,
        isModerator = isModerator,
        registrationDate = ZonedDateTime.parse(
            registrationDate,
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.systemDefault())
        ).toLocalDateTime()
    )
}

fun FirebaseUser.toAuthUser(): AuthUser {
    return AuthUser(
        id = uid,
        username = email.toString(),
        email = email.toString()
    )
}


fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        username = username,
        email = email,
        bio = bio,
        profileAvatarUrl = profileAvatarUrl,
        profileBackgroundUrl = profileBackgroundUrl,
        registrationDate = -1
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
        registrationDate = LocalDateTime.now()
    )
}