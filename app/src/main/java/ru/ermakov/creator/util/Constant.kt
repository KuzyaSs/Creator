package ru.ermakov.creator.util

class Constant {
    companion object {
        const val EMPTY_STRING = ""
        const val INITIAL_MONEY = 0

        const val NETWORK_EXCEPTION = "A network error"
        const val EMPTY_DATA_EXCEPTION = "Fill in all the fields"
        const val EMAIL_FORMAT_EXCEPTION = "The email address is badly formatted"
        const val PASSWORDS_DON_T_MATCH_EXCEPTION = "Passwords don't match"
        const val WEAK_PASSWORD_EXCEPTION = "Password should be at least 6 characters"
        const val EMAIL_COLLISION_EXCEPTION = "The email address is already in use by another account"
        const val INVALID_USER_EXCEPTION = "Invalid user"
        const val UNKNOWN_EXCEPTION = "Unknown error"

        const val USERS_COLLECTION = "Users"
    }
}