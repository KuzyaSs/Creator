package ru.ermakov.creator.domain.exception

class ErrorConstants {
    companion object {
        const val NETWORK_EXCEPTION = "Network error"
        const val EMPTY_DATA_EXCEPTION = "Fill in all the fields"
        const val EMAIL_FORMAT_EXCEPTION = "The email address is badly formatted"
        const val EMAIL_COLLISION_EXCEPTION = "The email address is already in use by another account"
        const val EMAIL_VERIFICATION_EXCEPTION = "Verify your account email"
        const val INVALID_PASSWORD_EXCEPTION = "The password is invalid or the user does not have a password"
        const val PASSWORD_MISMATCH_EXCEPTION = "Passwords don't match"
        const val WEAK_PASSWORD_EXCEPTION = "Password should be at least 6 characters"
        const val TOO_MANY_REQUESTS_EXCEPTION = "The account has been blocked. Reset your password or try again later"
        const val INVALID_USER_EXCEPTION = "There is no user record with this email"
        const val UNKNOWN_EXCEPTION = "Unknown error"
    }
}