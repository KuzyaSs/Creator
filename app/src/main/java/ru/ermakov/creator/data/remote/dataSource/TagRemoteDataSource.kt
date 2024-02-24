package ru.ermakov.creator.data.remote.dataSource

import ru.ermakov.creator.domain.model.Tag
import ru.ermakov.creator.domain.model.TagRequest

interface TagRemoteDataSource {
    suspend fun getTagsByCreatorId(creatorId: String): List<Tag>

    suspend fun getTagById(tagId: Long): Tag

    suspend fun insertTag(tagRequest: TagRequest)

    suspend fun updateTag(tagId: Long, tagRequest: TagRequest)

    suspend fun deleteTagById(tagId: Long)
}