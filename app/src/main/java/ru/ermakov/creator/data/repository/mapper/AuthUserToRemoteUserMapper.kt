package ru.ermakov.creator.data.repository.mapper

import com.google.firebase.auth.FirebaseUser
import ru.ermakov.creator.data.storage.remote.model.RemoteUser
import ru.ermakov.creator.util.Constant.Companion.EMPTY_STRING

class AuthUserToRemoteUserMapper : Mapper<FirebaseUser, RemoteUser> {
    override fun map(source: FirebaseUser): RemoteUser {
        source.apply {
            return RemoteUser(
                email = email.toString(),
                username = uid,
                image = EMPTY_STRING,
                backgroundImage = EMPTY_STRING,
                about = EMPTY_STRING,
            )
        }
    }
}