package ru.ermakov.creator.domain.useCase.donateToCreditGoal

import ru.ermakov.creator.domain.exception.InvalidTransactionAmountException
import ru.ermakov.creator.domain.model.CreditGoalTransactionRequest
import ru.ermakov.creator.domain.repository.TransactionRepository

class InsertCreditGoalTransactionUseCase(private val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(creditGoalTransactionRequest: CreditGoalTransactionRequest) {
        if (creditGoalTransactionRequest.amount <= 0) {
            throw InvalidTransactionAmountException()
        }
        transactionRepository.insertCreditGoalTransaction(
            creditGoalTransactionRequest = creditGoalTransactionRequest
        )
    }
}