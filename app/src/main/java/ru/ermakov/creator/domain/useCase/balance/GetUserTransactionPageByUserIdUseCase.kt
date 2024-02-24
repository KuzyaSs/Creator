package ru.ermakov.creator.domain.useCase.balance

import ru.ermakov.creator.domain.model.UserTransactionItem
import ru.ermakov.creator.domain.repository.TransactionRepository
import ru.ermakov.creator.domain.useCase.shared.GetDateTimeUseCase

class GetUserTransactionPageByUserIdUseCase(
    private val transactionRepository: TransactionRepository,
    private val getUserTransactionAmountUseCase: GetUserTransactionAmountUseCase,
    private val getDateTimeUseCase: GetDateTimeUseCase
) {
    suspend operator fun invoke(
        userId: String,
        userTransactionId: Long
    ): List<UserTransactionItem> {
        return transactionRepository.getUserTransactionPageByUserId(
            userId = userId,
            userTransactionId = userTransactionId
        ).map { userTransaction ->
            val user = if (userTransaction.senderUser.id != userId) {
                userTransaction.senderUser
            } else {
                userTransaction.receiverUser
            }

            UserTransactionItem(
                id = userTransaction.id,
                title = user.username,
                user = user,
                transactionType = userTransaction.transactionType.name,
                amount = getUserTransactionAmountUseCase(
                    userTransaction = userTransaction,
                    userId = userId
                ),
                message = userTransaction.message,
                date = getDateTimeUseCase(userTransaction.transactionDate)
            )
        }
    }
}