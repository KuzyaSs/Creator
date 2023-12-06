package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.domain.model.Creator

interface CreatorRemoteDataSource {
    suspend fun getCreatorsByPage(searchQuery: String, page: Int): List<Creator>

    suspend fun getCreatorByUserId(userId: String): Creator
}