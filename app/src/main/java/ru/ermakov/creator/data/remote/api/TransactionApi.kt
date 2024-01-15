package ru.ermakov.creator.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.ermakov.creator.data.remote.model.RemoteCreditGoalTransaction
import ru.ermakov.creator.data.remote.model.RemoteCreditGoalTransactionRequest
import ru.ermakov.creator.data.remote.model.RemoteUserTransaction
import ru.ermakov.creator.data.remote.model.RemoteUserTransactionRequest

interface TransactionApi {
    @GET("users/{userId}/transactions")
    suspend fun getUserTransactionPageByUserId(
        @Path("userId") userId: String,
        @Query("userTransactionId") userTransactionId: Long,
        @Query("limit") limit: Int
    ): Response<List<RemoteUserTransaction>>

    @GET("credit-goals/{creditGoalId}/transactions")
    suspend fun getCreditGoalTransactionPageByCreditGoalId(
        @Path("creditGoalId") creditGoalId: Long,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<List<RemoteCreditGoalTransaction>>

    @GET("users/{userId}/balance")
    suspend fun getBalanceByUserId(@Path("userId") userId: String): Response<Long>

    @GET("credit-goals/{creditGoalId}/balance")
    suspend fun getBalanceByCreditGoalId(@Path("creditGoalId") creditGoalId: Long): Response<Long>

    @POST("user-transactions")
    suspend fun insertUserTransaction(
        @Body remoteUserTransactionRequest: RemoteUserTransactionRequest
    ): Response<Unit>

    @POST("credit-goal-transactions")
    suspend fun insertCreditGoalTransaction(
        @Body remoteCreditGoalTransactionRequest: RemoteCreditGoalTransactionRequest
    ): Response<Unit>
}