package ru.ermakov.creator.data.repository

import ru.ermakov.creator.data.remote.dataSource.CreditGoalRemoteDataSource
import ru.ermakov.creator.domain.model.CreditGoal
import ru.ermakov.creator.domain.model.CreditGoalRequest
import ru.ermakov.creator.domain.repository.CreditGoalRepository

class CreditGoalRepositoryImpl(
    private val creditGoalRemoteDataSource: CreditGoalRemoteDataSource
) : CreditGoalRepository {
    override suspend fun getCreditGoalsByCreatorId(creatorId: String): List<CreditGoal> {
        return creditGoalRemoteDataSource.getCreditGoalsByCreatorId(creatorId = creatorId)
    }

    override suspend fun getCreditGoalById(creditGoalId: Long): CreditGoal {
        return creditGoalRemoteDataSource.getCreditGoalById(creditGoalId = creditGoalId)
    }

    override suspend fun insertCreditGoal(creditGoalRequest: CreditGoalRequest) {
        creditGoalRemoteDataSource.insertCreditGoal(creditGoalRequest = creditGoalRequest)
    }

    override suspend fun updateCreditGoal(
        creditGoalId: Long,
        creditGoalRequest: CreditGoalRequest
    ) {
        creditGoalRemoteDataSource.updateCreditGoal(
            creditGoalId = creditGoalId,
            creditGoalRequest = creditGoalRequest
        )
    }

    override suspend fun deleteCreditGoalById(creditGoalId: Long) {
        creditGoalRemoteDataSource.deleteCreditGoalById(creditGoalId = creditGoalId)
    }

}