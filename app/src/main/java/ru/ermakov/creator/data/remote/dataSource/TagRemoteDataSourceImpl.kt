package ru.ermakov.creator.data.remote.dataSource

import android.util.Log
import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.mapper.toRemotePostRequest
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
            Log.d("MY_TAG", "getTagsByCreatorId SUCCESS ${remoteTagsResponse.body()}")
            remoteTagsResponse.body()?.let { remoteTags ->
                return remoteTags.map { remoteTag ->
                    remoteTag.toTag()
                }
            }
        }
        Log.d("MY_TAG", "getTagsByCreatorId ERROR ${remoteTagsResponse.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = remoteTagsResponse)
    }

    override suspend fun getTagById(tagId: Long): Tag {
        val remoteTagResponse = tagApi.getTagById(tagId = tagId)
        if (remoteTagResponse.isSuccessful) {
            Log.d("MY_TAG", "getTagById SUCCESS ${remoteTagResponse.body()}")
            remoteTagResponse.body()?.let { remoteTag ->
                return remoteTag.toTag()
            }
        }
        Log.d("MY_TAG", "getTagById ERROR ${remoteTagResponse.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = remoteTagResponse)
    }

    override suspend fun insertTag(tagRequest: TagRequest) {
        val response = tagApi.insertTag(remoteTagRequest = tagRequest.toRemoteTagRequest())
        if (response.isSuccessful) {
            Log.d("MY_TAG", "insertTag SUCCESS ${response.body()}")
            return
        }
        Log.d("MY_TAG", "insertTag ERROR ${response.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun updateTag(tagId: Long, tagRequest: TagRequest) {
        val response = tagApi.updateTag(
            tagId = tagId,
            remoteTagRequest = tagRequest.toRemoteTagRequest()
        )
        if (response.isSuccessful) {
            Log.d("MY_TAG", "updateTag SUCCESS ${response.body()}")
            return
        }
        Log.d("MY_TAG", "updateTag ERROR ${response.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }

    override suspend fun deleteTagById(tagId: Long) {
        val response = tagApi.deleteTagById(tagId = tagId)
        if (response.isSuccessful) {
            Log.d("MY_TAG", "deleteTagById SUCCESS ${response.body()}")
            return
        }
        Log.d("MY_TAG", "deleteTagById ERROR ${response.errorBody()}")
        throw apiExceptionLocalizer.localizeApiException(response = response)
    }
}