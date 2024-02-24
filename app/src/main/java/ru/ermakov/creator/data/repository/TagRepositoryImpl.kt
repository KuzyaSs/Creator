package ru.ermakov.creator.data.repository

import ru.ermakov.creator.data.remote.dataSource.TagRemoteDataSource
import ru.ermakov.creator.domain.model.Tag
import ru.ermakov.creator.domain.model.TagRequest
import ru.ermakov.creator.domain.repository.TagRepository

class TagRepositoryImpl(private val tagRemoteDataSource: TagRemoteDataSource) : TagRepository {
    override suspend fun getTagsByCreatorId(creatorId: String): List<Tag> {
        return tagRemoteDataSource.getTagsByCreatorId(creatorId = creatorId)
    }

    override suspend fun getTagById(tagId: Long): Tag {
        return tagRemoteDataSource.getTagById(tagId = tagId)

    }

    override suspend fun insertTag(tagRequest: TagRequest) {
        tagRemoteDataSource.insertTag(tagRequest = tagRequest)
    }

    override suspend fun updateTag(tagId: Long, tagRequest: TagRequest) {
        tagRemoteDataSource.updateTag(tagId = tagId, tagRequest = tagRequest)
    }

    override suspend fun deleteTagById(tagId: Long) {
        tagRemoteDataSource.deleteTagById(tagId = tagId)
    }
}