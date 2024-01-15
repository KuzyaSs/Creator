package ru.ermakov.creator.data.repository

import ru.ermakov.creator.data.remote.dataSource.TransactionRemoteDataSource
import ru.ermakov.creator.domain.model.CreditGoalTransaction
import ru.ermakov.creator.domain.model.CreditGoalTransactionRequest
import ru.ermakov.creator.domain.model.UserTransaction
import ru.ermakov.creator.domain.model.UserTransactionRequest
import ru.ermakov.creator.domain.repository.TransactionRepository

class TransactionRepositoryImpl(
    private val transactionRemoteDataSource: TransactionRemoteDataSource
) : TransactionRepository {
    override suspend fun getUserTransactionPageByUserId(
        userId: String,
        userTransactionId: Long
    ): List<UserTransaction> {
        return transactionRemoteDataSource.getUserTransactionPageByUserId(
            userId = userId,
            userTransactionId = userTransactionId
        )
    }

    override suspend fun getCreditGoalTransactionPageByCreditGoalId(
        creditGoalId: Long,
        page: Int
    ): List<CreditGoalTransaction> {
        return transactionRemoteDataSource.getCreditGoalTransactionPageByCreditGoalId(
            creditGoalId = creditGoalId,
            page = page
        )
    }

    override suspend fun getBalanceByUserId(userId: String): Long {
        return transactionRemoteDataSource.getBalanceByUserId(userId = userId)
    }

    override suspend fun getBalanceByCreditGoalId(creditGoalId: Long): Long {
        return transactionRemoteDataSource.getBalanceByCreditGoalId(creditGoalId = creditGoalId)
    }

    override suspend fun insertUserTransaction(userTransactionRequest: UserTransactionRequest) {
        transactionRemoteDataSource.insertUserTransaction(userTransactionRequest = userTransactionRequest)
    }

    override suspend fun insertCreditGoalTransaction(creditGoalTransactionRequest: CreditGoalTransactionRequest) {
        transactionRemoteDataSource.insertCreditGoalTransaction(
            creditGoalTransactionRequest = creditGoalTransactionRequest
        )
    }
}