package ru.ermakov.creator.domain.useCase.editTag

import ru.ermakov.creator.domain.model.Tag
import ru.ermakov.creator.domain.repository.TagRepository

class GetTagByIdUseCase(private val tagRepository: TagRepository) {
    suspend operator fun invoke(tagId: Long): Tag {
        return tagRepository.getTagById(tagId = tagId)
    }
}