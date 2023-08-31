package ru.ermakov.creator.data.repository.mapper

import com.google.firebase.auth.FirebaseUser
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.util.Constant.Companion.EMPTY_STRING

class AuthUserToDomainUserMapper : Mapper<FirebaseUser, User> {
    override fun map(source: FirebaseUser): User {
        source.apply {
            return User(
                id = uid,
                email = email.toString(),
                username = uid,
                image = EMPTY_STRING,
                backgroundImage = EMPTY_STRING,
                about = EMPTY_STRING,
            )
        }
    }
}