package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.mapper.toCreator
import ru.ermakov.creator.data.remote.api.CreatorApi
import ru.ermakov.creator.domain.model.Creator

private const val LIMIT = 20

class CreatorRemoteDataSourceImpl(
    private val creatorApi: CreatorApi,
    private val apiExceptionLocalizer: ApiExceptionLocalizer
) : CreatorRemoteDataSource {
    override suspend fun getCreatorsByPage(searchQuery: String, page: Int): List<Creator> {
        val remoteCreatorsResponse = creatorApi.getCreatorsByPage(
            searchQuery = searchQuery,
            limit = LIMIT,
            offset = LIMIT * page
        )
        if (remoteCreatorsResponse.isSuccessful) {
            remoteCreatorsResponse.body()?.let { remoteCreators ->
                return remoteCreators.map { remoteCreator -> remoteCreator.toCreator() }
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remoteCreatorsResponse)
    }

    override suspend fun getCreatorByUserId(userId: String): Creator {
        val remoteCreatorResponse = creatorApi.getCreatorByUserId(userId = userId)
        if (remoteCreatorResponse.isSuccessful) {
            remoteCreatorResponse.body()?.let { remoteCreator ->
                return remoteCreator.toCreator()
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remoteCreatorResponse)
    }
}