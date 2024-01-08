package ru.ermakov.creator.data.repository

import ru.ermakov.creator.data.remote.dataSource.FollowRemoteDataSource
import ru.ermakov.creator.domain.model.Follow
import ru.ermakov.creator.domain.repository.FollowRepository

class FollowRepositoryImpl(
    private val followRemoteDataSource: FollowRemoteDataSource
) : FollowRepository {
    override suspend fun getFollowPageByUserId(
        searchQuery: String,
        page: Int,
        userId: String
    ): List<Follow> {
        return followRemoteDataSource.getFollowPageByUserId(
            searchQuery = searchQuery,
            page = page,
            userId = userId
        )
    }

    override suspend fun getFollowByUserAndCreatorIds(userId: String, creatorId: String): Follow {
        return followRemoteDataSource.getFollowByUserAndCreatorIds(
            userId = userId,
            creatorId = creatorId
        )
    }

    override suspend fun getFollowerCountByUserId(userId: String): Long {
        return followRemoteDataSource.getFollowerCountByUserId(userId = userId)
    }

    override suspend fun insertFollow(userId: String, creatorId: String) {
        followRemoteDataSource.insertFollow(userId = userId, creatorId = creatorId)
    }

    override suspend fun deleteFollowById(followId: Long) {
        followRemoteDataSource.deleteFollowById(followId = followId)
    }
}