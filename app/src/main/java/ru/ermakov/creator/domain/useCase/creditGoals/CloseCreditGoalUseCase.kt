package ru.ermakov.creator.domain.useCase.creditGoals

import ru.ermakov.creator.domain.exception.UnexpectedValueException
import ru.ermakov.creator.domain.model.CreditGoal
import ru.ermakov.creator.domain.model.CreditGoalTransactionRequest
import ru.ermakov.creator.domain.repository.TransactionRepository

private const val CREDIT_GOAL_CLOSURE_TRANSACTION_ID = 3L

class CloseCreditGoalUseCase(private val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(creditGoal: CreditGoal?, senderUserId: String, creatorId: String) {
        if (creditGoal == null) {
            throw UnexpectedValueException()
        }
        val creditGoalTransactionRequest = CreditGoalTransactionRequest(
            creditGoalId = creditGoal.id,
            senderUserId = senderUserId,
            receiverUserId = creatorId,
            transactionTypeId = CREDIT_GOAL_CLOSURE_TRANSACTION_ID,
            amount = creditGoal.balance,
            message = ""
        )

        transactionRepository.insertCreditGoalTransaction(
            creditGoalTransactionRequest = creditGoalTransactionRequest
        )
    }
}