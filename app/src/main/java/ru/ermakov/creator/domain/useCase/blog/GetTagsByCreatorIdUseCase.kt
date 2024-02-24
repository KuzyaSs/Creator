package ru.ermakov.creator.domain.useCase.blog

import ru.ermakov.creator.domain.model.Tag
import ru.ermakov.creator.domain.repository.TagRepository

class GetTagsByCreatorIdUseCase(private val tagRepository: TagRepository) {
    suspend operator fun invoke(creatorId: String): List<Tag> {
        return tagRepository.getTagsByCreatorId(creatorId = creatorId)
    }
}