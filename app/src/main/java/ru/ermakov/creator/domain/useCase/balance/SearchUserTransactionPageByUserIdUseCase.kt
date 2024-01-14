package ru.ermakov.creator.domain.useCase.balance

import ru.ermakov.creator.domain.model.UserTransactionItem
import ru.ermakov.creator.domain.repository.TransactionRepository
import java.time.format.DateTimeFormatter

class SearchUserTransactionPageByUserIdUseCase(
    private val transactionRepository: TransactionRepository,
    private val getUserTransactionAmountUseCase: GetUserTransactionAmountUseCase
) {
    suspend operator fun invoke(userId: String, page: Int): List<UserTransactionItem> {
        return transactionRepository.getUserTransactionPageByUserId(
            userId = userId,
            page = page
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
                date = userTransaction.transactionDate.format(
                    DateTimeFormatter.ofPattern("dd.MM.yyyy")
                )
            )
        }
    }
}