package ru.ermakov.creator.domain.useCase.tip

import ru.ermakov.creator.domain.exception.InvalidTipAmountException
import ru.ermakov.creator.domain.model.UserTransactionRequest
import ru.ermakov.creator.domain.repository.TransactionRepository

class SendTipUseCase(private val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(userTransactionRequest: UserTransactionRequest) {
        if (userTransactionRequest.amount <= 0) {
            throw InvalidTipAmountException()
        }
        transactionRepository.insertUserTransaction(userTransactionRequest = userTransactionRequest)
    }
}