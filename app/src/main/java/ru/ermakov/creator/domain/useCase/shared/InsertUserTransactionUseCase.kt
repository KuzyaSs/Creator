package ru.ermakov.creator.domain.useCase.shared

import ru.ermakov.creator.domain.exception.InvalidTransactionAmountException
import ru.ermakov.creator.domain.model.UserTransactionRequest
import ru.ermakov.creator.domain.repository.TransactionRepository

class InsertUserTransactionUseCase(private val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(userTransactionRequest: UserTransactionRequest) {
        if (userTransactionRequest.amount <= 0) {
            throw InvalidTransactionAmountException()
        }
        transactionRepository.insertUserTransaction(userTransactionRequest = userTransactionRequest)
    }
}