package ru.ermakov.creator.data.repository.mapper

import com.google.firebase.auth.FirebaseUser
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.util.Constant.Companion.EMPTY_STRING
import ru.ermakov.creator.util.Constant.Companion.INITIAL_MONEY

class AuthUserToDomainUserMapper : Mapper<FirebaseUser, User> {
    override fun map(data: FirebaseUser): User {
        data.apply {
            return User(
                id = uid,
                email = email.toString(),
                username = uid,
                image = EMPTY_STRING,
                money = INITIAL_MONEY,
                about = EMPTY_STRING,
            )
        }
    }
}