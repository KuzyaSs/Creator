package ru.ermakov.creator.data.remote.dataSource

import android.util.Log
import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.mapper.toCreditGoal
import ru.ermakov.creator.data.mapper.toRemoteCreditGoalRequest
import ru.ermakov.creator.data.remote.api.CreditGoalApi
import ru.ermakov.creator.domain.model.CreditGoal
import ru.ermakov.creator.domain.model.CreditGoalRequest

class CreditGoalRemoteDataSourceImpl(
    private val creditGoalApi: CreditGoalApi,
    private val apiExceptionLocalizer: ApiExceptionLocalizer
) : CreditGoalRemoteDataSource {
    override suspend fun getCreditGoalsByCreatorId(creatorId: String): List<CreditGoal> {
        val remoteCreditGoalsResponse = creditGoalApi.getCreditGoalsByCreatorId(
            creatorId = creatorId
        )
        if (remoteCreditGoalsResponse.isSuccessful) {
            remoteCreditGoalsResponse.body()?.let { remoteCreditGoals ->
                return remoteCreditGoals.map { remoteCreditGoal ->
                    remoteCreditGoal.toCreditGoal()
                }
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remoteCreditGoalsResponse)
    }

    override suspend fun getCreditGoalById(creditGoalId: Long): CreditGoal {
        val remoteCreditGoalResponse = creditGoalApi.getCreditGoalById(
            creditGoalId = creditGoalId
        )
        if (remoteCreditGoalResponse.isSuccessful) {
            Log.d("MY_TAG", "getCreditGoalById SUCCESS ${remoteCreditGoalResponse.body()}")
            remoteCreditGoalResponse.body()?.let { remoteCreditGoal ->
                return remoteCreditGoal.toCreditGoal()
            }
        }
        Log.d("MY_TAG", "getCreditGoalById ERROR ${remoteCreditGoalResponse.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = remoteCreditGoalResponse)
    }

    override suspend fun insertCreditGoal(creditGoalRequest: CreditGoalRequest) {
        val response = creditGoalApi.insertCreditGoal(
            remoteCreditGoalRequest = creditGoalRequest.toRemoteCreditGoalRequest()
        )
        if (response.isSuccessful) {
            Log.d("MY_TAG", "insertCreditGoal SUCCESS ${response.body()}")
            return
        }
        Log.d("MY_TAG", "insertCreditGoal ERROR ${response.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun updateCreditGoal(
        creditGoalId: Long,
        creditGoalRequest: CreditGoalRequest
    ) {
        val response = creditGoalApi.updateCreditGoal(
            creditGoalId = creditGoalId,
            remoteCreditGoalRequest = creditGoalRequest.toRemoteCreditGoalRequest()
        )
        if (response.isSuccessful) {
            Log.d("MY_TAG", "updateCreditGoal SUCCESS ${response.body()}")
            return
        }
        Log.d("MY_TAG", "updateCreditGoal ERROR ${response.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun deleteCreditGoalById(creditGoalId: Long) {
        val response = creditGoalApi.deleteCreditGoalById(creditGoalId = creditGoalId)
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

}