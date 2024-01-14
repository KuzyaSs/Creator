package ru.ermakov.creator.domain.useCase.balance

import ru.ermakov.creator.domain.model.UserTransaction

private const val TOP_UP_TRANSACTION_TYPE_ID = 1L
private const val WITHDRAWAL_TRANSACTION_TYPE_ID = 2L
private const val CREDIT_GOAL_CLOSURE_TRANSACTION_TYPE_ID = 3L

class GetUserTransactionAmountUseCase {
    operator fun invoke(userTransaction: UserTransaction, userId: String): Long {
        return when (userTransaction.transactionType.id) {
            TOP_UP_TRANSACTION_TYPE_ID, CREDIT_GOAL_CLOSURE_TRANSACTION_TYPE_ID -> {
                userTransaction.amount
            }

            WITHDRAWAL_TRANSACTION_TYPE_ID -> {
                userTransaction.amount * (-1)

            }

            /*
            SUBSCRIPTION_PURCHASE_TRANSACTION_TYPE_ID (4),
            TRANSFER_TO_USER_TRANSACTION_TYPE_ID (5),
            TRANSFER_TO_CREDIT_GOAL_TRANSACTION_TYPE_ID(6)
            */
            else -> {
                if (userTransaction.senderUser.id == userId) {
                    userTransaction.amount * (-1)

                } else {
                    userTransaction.amount
                }
            }
        }
    }
}