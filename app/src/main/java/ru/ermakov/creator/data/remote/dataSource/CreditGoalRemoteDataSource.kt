package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.domain.model.CreditGoal
import ru.ermakov.creator.domain.model.CreditGoalRequest

interface CreditGoalRemoteDataSource {
    suspend fun getCreditGoalsByCreatorId(creatorId: String): List<CreditGoal>
    suspend fun getCreditGoalById(creditGoalId: Long): CreditGoal
    suspend fun insertCreditGoal(creditGoalRequest: CreditGoalRequest)
    suspend fun updateCreditGoal(creditGoalId: Long, creditGoalRequest: CreditGoalRequest)
    suspend fun deleteCreditGoalById(creditGoalId: Long)
}