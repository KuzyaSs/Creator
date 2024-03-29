package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.mapper.toCreditGoalTransaction
import ru.ermakov.creator.data.mapper.toRemoteCreditGoalTransactionRequest
import ru.ermakov.creator.data.mapper.toRemoteUserTransactionRequest
import ru.ermakov.creator.data.mapper.toUserTransaction
import ru.ermakov.creator.data.remote.api.TransactionApi
import ru.ermakov.creator.domain.model.CreditGoalTransaction
import ru.ermakov.creator.domain.model.CreditGoalTransactionRequest
import ru.ermakov.creator.domain.model.UserTransaction
import ru.ermakov.creator.domain.model.UserTransactionRequest

private const val LIMIT = 20

class TransactionRemoteDataSourceImpl(
    private val transactionApi: TransactionApi,
    private val apiExceptionLocalizer: ApiExceptionLocalizer
) : TransactionRemoteDataSource {
    override suspend fun getUserTransactionPageByUserId(
        userId: String,
        userTransactionId: Long
    ): List<UserTransaction> {
        val remoteUserTransactionsResponse = transactionApi.getUserTransactionPageByUserId(
            userId = userId,
            userTransactionId = userTransactionId,
            limit = LIMIT,
        )
        if (remoteUserTransactionsResponse.isSuccessful) {
            remoteUserTransactionsResponse.body()?.let { remoteUserTransactions ->

                return remoteUserTransactions.map { remoteUserTransaction ->
                    remoteUserTransaction.toUserTransaction()
                }
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remoteUserTransactionsResponse)
    }

    override suspend fun getCreditGoalTransactionPageByCreditGoalId(
        creditGoalId: Long,
        page: Int,
    ): List<CreditGoalTransaction> {
        val remoteCreditGoalTransactionsResponse =
            transactionApi.getCreditGoalTransactionPageByCreditGoalId(
                creditGoalId = creditGoalId,
                limit = LIMIT,
                offset = LIMIT * page
            )
        if (remoteCreditGoalTransactionsResponse.isSuccessful) {
            remoteCreditGoalTransactionsResponse.body()?.let { remoteCreditGoalTransactions ->
                return remoteCreditGoalTransactions.map { remoteCreditGoalTransaction ->
                    remoteCreditGoalTransaction.toCreditGoalTransaction()
                }
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remoteCreditGoalTransactionsResponse)
    }

    override suspend fun getBalanceByUserId(userId: String): Long {
        val balanceResponse = transactionApi.getBalanceByUserId(userId = userId)
        if (balanceResponse.isSuccessful) {
            balanceResponse.body()?.let { balance ->
                return balance
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = balanceResponse)
    }

    override suspend fun getBalanceByCreditGoalId(creditGoalId: Long): Long {
        val balanceResponse = transactionApi.getBalanceByCreditGoalId(creditGoalId = creditGoalId)
        if (balanceResponse.isSuccessful) {
            balanceResponse.body()?.let { balance ->
                return balance
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = balanceResponse)
    }

    override suspend fun insertUserTransaction(userTransactionRequest: UserTransactionRequest) {
        val response = transactionApi.insertUserTransaction(
            remoteUserTransactionRequest = userTransactionRequest.toRemoteUserTransactionRequest()
        )
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun insertCreditGoalTransaction(
        creditGoalTransactionRequest: CreditGoalTransactionRequest
    ) {
        val response = transactionApi.insertCreditGoalTransaction(
            remoteCreditGoalTransactionRequest = creditGoalTransactionRequest
                .toRemoteCreditGoalTransactionRequest()
        )
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }
}