package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.mapper.toRemoteTagRequest
import ru.ermakov.creator.data.mapper.toTag
import ru.ermakov.creator.data.remote.api.TagApi
import ru.ermakov.creator.domain.model.Tag
import ru.ermakov.creator.domain.model.TagRequest

class TagRemoteDataSourceImpl(
    private val tagApi: TagApi,
    private val apiExceptionLocalizer: ApiExceptionLocalizer
) : TagRemoteDataSource {
    override suspend fun getTagsByCreatorId(creatorId: String): List<Tag> {
        val remoteTagsResponse = tagApi.getTagsByCreatorId(creatorId = creatorId)
        if (remoteTagsResponse.isSuccessful) {
            remoteTagsResponse.body()?.let { remoteTags ->
                return remoteTags.map { remoteTag ->
                    remoteTag.toTag()
                }
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remoteTagsResponse)
    }

    override suspend fun getTagById(tagId: Long): Tag {
        val remoteTagResponse = tagApi.getTagById(tagId = tagId)
        if (remoteTagResponse.isSuccessful) {
            remoteTagResponse.body()?.let { remoteTag ->
                return remoteTag.toTag()
            }
        }
        throw apiExceptionLocalizer.localizeApiException(response = remoteTagResponse)
    }

    override suspend fun insertTag(tagRequest: TagRequest) {
        val response = tagApi.insertTag(remoteTagRequest = tagRequest.toRemoteTagRequest())
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun updateTag(tagId: Long, tagRequest: TagRequest) {
        val response = tagApi.updateTag(
            tagId = tagId,
            remoteTagRequest = tagRequest.toRemoteTagRequest()
        )
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun deleteTagById(tagId: Long) {
        val response = tagApi.deleteTagById(tagId = tagId)
        if (response.isSuccessful) {
            return
        }
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }
}