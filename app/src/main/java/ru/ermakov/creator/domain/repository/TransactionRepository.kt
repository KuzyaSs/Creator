package ru.ermakov.creator.domain.repository

import ru.ermakov.creator.domain.model.CreditGoalTransaction
import ru.ermakov.creator.domain.model.CreditGoalTransactionRequest
import ru.ermakov.creator.domain.model.UserTransaction
import ru.ermakov.creator.domain.model.UserTransactionRequest

interface TransactionRepository {
    suspend fun getUserTransactionPageByUserId(
        userId: String,
        userTransactionId: Long
    ): List<UserTransaction>

    suspend fun getCreditGoalTransactionPageByCreditGoalId(
        creditGoalId: Long,
        page: Int,
    ): List<CreditGoalTransaction>

    suspend fun getBalanceByUserId(userId: String): Long

    suspend fun getBalanceByCreditGoalId(creditGoalId: Long): Long

    suspend fun insertUserTransaction(userTransactionRequest: UserTransactionRequest)

    suspend fun insertCreditGoalTransaction(creditGoalTransactionRequest: CreditGoalTransactionRequest)
}