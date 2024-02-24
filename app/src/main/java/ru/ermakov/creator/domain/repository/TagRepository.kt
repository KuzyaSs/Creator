package ru.ermakov.creator.domain.repository

import ru.ermakov.creator.domain.model.Tag
import ru.ermakov.creator.domain.model.TagRequest

interface TagRepository {
    suspend fun getTagsByCreatorId(creatorId: String): List<Tag>

    suspend fun getTagById(tagId: Long): Tag

    suspend fun insertTag(tagRequest: TagRequest)

    suspend fun updateTag(tagId: Long, tagRequest: TagRequest)

    suspend fun deleteTagById(tagId: Long)
}