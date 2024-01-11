package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.domain.model.CreditGoalTransaction
import ru.ermakov.creator.domain.model.CreditGoalTransactionRequest
import ru.ermakov.creator.domain.model.UserTransaction
import ru.ermakov.creator.domain.model.UserTransactionRequest

interface TransactionRemoteDataSource {
    suspend fun getUserTransactionPageByUserId(userId: String, page: Int): List<UserTransaction>

    suspend fun getCreditGoalTransactionPageByCreditGoalId(
        creditGoalId: Long,
        page: Int
    ): List<CreditGoalTransaction>

    suspend fun getBalanceByUserId(userId: String): Long

    suspend fun getBalanceByCreditGoalId(creditGoalId: Long): Long

    suspend fun insertUserTransaction(userTransactionRequest: UserTransactionRequest)

    suspend fun insertCreditGoalTransaction(creditGoalTransactionRequest: CreditGoalTransactionRequest)
}