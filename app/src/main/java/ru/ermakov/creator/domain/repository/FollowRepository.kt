package ru.ermakov.creator.domain.repository

import ru.ermakov.creator.domain.model.Follow

interface FollowRepository {
    suspend fun getFollowPageByUserId(
        searchQuery: String,
        page: Int,
        userId: String
    ): List<Follow>

    suspend fun getFollowByUserAndCreatorIds(userId: String, creatorId: String): Follow

    suspend fun getFollowerCountByUserId(userId: String): Long

    suspend fun insertFollow(userId: String, creatorId: String)

    suspend fun deleteFollowById(followId: Long)
}