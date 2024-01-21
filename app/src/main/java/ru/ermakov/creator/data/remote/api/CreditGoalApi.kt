package ru.ermakov.creator.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.ermakov.creator.data.remote.model.RemoteCreditGoal
import ru.ermakov.creator.data.remote.model.RemoteCreditGoalRequest

interface CreditGoalApi {
    @GET("creators/{creatorId}/credit-goals")
    suspend fun getCreditGoalsByCreatorId(
        @Path("creatorId") creatorId: String
    ): Response<List<RemoteCreditGoal>>

    @GET("credit-goals/{creditGoalId}")
    suspend fun getCreditGoalById(@Path("creditGoalId") creditGoalId: Long): Response<RemoteCreditGoal>

    @POST("credit-goals")
    suspend fun insertCreditGoal(
        @Body remoteCreditGoalRequest: RemoteCreditGoalRequest
    ): Response<Unit>

    @PUT("credit-goals/{creditGoalId}")
    suspend fun updateCreditGoal(
        @Path("creditGoalId") creditGoalId: Long,
        @Body remoteCreditGoalRequest: RemoteCreditGoalRequest
    ): Response<Unit>

    @DELETE("credit-goals/{creditGoalId}")
    suspend fun deleteCreditGoalById(@Path("creditGoalId") creditGoalId: Long): Response<Unit>
}