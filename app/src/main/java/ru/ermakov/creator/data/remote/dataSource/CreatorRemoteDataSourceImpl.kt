package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.remote.api.CreatorApi
import ru.ermakov.creator.domain.model.Creator

class CreatorRemoteDataSourceImpl(
    private val creatorApi: CreatorApi,
    private val apiExceptionLocalizer: ApiExceptionLocalizer
) : CreatorRemoteDataSource {
    override suspend fun getCreatorsByPage(searchQuery: String, page: Int): List<Creator> {
        TODO("Not yet implemented")
    }

    override suspend fun getCreatorByUserId(userId: String): Creator {
        TODO("Not yet implemented")
    }
}