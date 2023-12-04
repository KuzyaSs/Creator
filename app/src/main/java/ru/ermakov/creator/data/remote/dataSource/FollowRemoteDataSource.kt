package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.domain.model.Follow

interface FollowRemoteDataSource {
    suspend fun getFollowsByUserId(userId: String): List<Follow>

    suspend fun getFollowByUserAndCreatorIds(userId: String, creatorId: String): Follow

    suspend fun getFollowerCountByUserId(userId: String): Long

    suspend fun insertFollow(userId: String, creatorId: String)

    suspend fun deleteFollowById(followId: Long)
}