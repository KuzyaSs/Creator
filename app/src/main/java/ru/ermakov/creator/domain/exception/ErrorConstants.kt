package ru.ermakov.creator.domain.exception

class ErrorConstants {
    companion object {
        const val NETWORK_EXCEPTION = "Network error"
        const val EMPTY_DATA_EXCEPTION = "Fill in all the fields"
        const val SHORT_USERNAME_EXCEPTION = "Username should be at least 3 characters long"
        const val DUPLICATE_USERNAME_EXCEPTION = "This username is already in use"
        const val USER_NOT_FOUND_EXCEPTION = "User is not found"
        const val FOLLOW_NOT_FOUND_EXCEPTION = "Follow is not found"
        const val SUBSCRIPTION_NOT_FOUND_EXCEPTION = "Subscription is not found"
        const val CREDIT_GOAL_NOT_FOUND_EXCEPTION = "Credit goal is not found"
        const val INVALID_SUBSCRIPTION_PRICE_EXCEPTION = "Subscription price must be more than 0 CR-credits"
        const val INVALID_CREDIT_GOAL_TARGET_BALANCE_EXCEPTION = "Target balance must be more than 0 CR-credits"
        const val INVALID_TRANSACTION_AMOUNT_EXCEPTION = "Enter the amount of CR-credit for the transaction"
        const val INVALID_CARD_NUMBER_LENGTH_EXCEPTION = "Card number must be 16 characters long"
        const val INVALID_VALIDITY_LENGTH_EXCEPTION = "Validity must be 4 characters long"
        const val INVALID_CVV_LENGTH_EXCEPTION = "CVV must be 3 characters long"
        const val DUPLICATE_SUBSCRIPTION_TITLE_EXCEPTION = "Subscription with the same title already exists"
        const val DUPLICATE_USER_SUBSCRIPTION_EXCEPTION = "User already has this subscription"
        const val EMAIL_FORMAT_EXCEPTION = "The email address is badly formatted"
        const val EMAIL_COLLISION_EXCEPTION = "The email address is already in use by another account"
        const val EMAIL_VERIFICATION_EXCEPTION = "Verify your account email"
        const val UNKNOWN_EMAIL_EXCEPTION = "There is no user record with this email"
        const val INVALID_PASSWORD_EXCEPTION = "The password is invalid or the user does not have a password"
        const val PASSWORD_MISMATCH_EXCEPTION = "Passwords don't match"
        const val WEAK_PASSWORD_EXCEPTION = "Password should be at least 6 characters"
        const val INSUFFICIENT_FUNDS_IN_ACCOUNT_EXCEPTION = "Insufficient funds in the account"
        const val INSUFFICIENT_FUNDS_IN_GOAL_EXCEPTION = "Insufficient funds in the goal"
        const val TRANSACTION_NOT_FOUND_EXCEPTION = "Transaction is not found"
        const val TOO_MANY_REQUESTS_EXCEPTION = "The account has been blocked. Reset your password or try again later"
        const val UNKNOWN_EXCEPTION = "Unknown error"
    }
}