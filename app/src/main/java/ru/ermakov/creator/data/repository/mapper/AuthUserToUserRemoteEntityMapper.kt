package ru.ermakov.creator.data.repository.mapper

import com.google.firebase.auth.FirebaseUser
import ru.ermakov.creator.data.remote.model.UserRemoteEntity
import ru.ermakov.creator.util.Constant.Companion.EMPTY_STRING
import ru.ermakov.creator.util.Constant.Companion.INITIAL_MONEY

class AuthUserToUserRemoteEntityMapper : Mapper<FirebaseUser, UserRemoteEntity> {
    override fun map(data: FirebaseUser): UserRemoteEntity {
        data.apply {
            return UserRemoteEntity(
                email = email.toString(),
                username = uid,
                image = EMPTY_STRING,
                money = INITIAL_MONEY,
                about = EMPTY_STRING,
            )
        }
    }
}