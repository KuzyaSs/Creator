package ru.ermakov.creator.data.repository

import ru.ermakov.creator.data.remote.dataSource.CreatorRemoteDataSource
import ru.ermakov.creator.domain.model.Creator
import ru.ermakov.creator.domain.repository.CreatorRepository

class CreatorRepositoryImpl(
    private val creatorRemoteDataSource: CreatorRemoteDataSource
) : CreatorRepository {
    override suspend fun getCreatorsByPage(searchQuery: String, page: Int): List<Creator> {
        return creatorRemoteDataSource.getCreatorsByPage(searchQuery = searchQuery, page = page)
    }

    override suspend fun getCreatorByUserId(userId: String): Creator {
        return creatorRemoteDataSource.getCreatorByUserId(userId = userId)
    }
}