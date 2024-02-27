package ru.ermakov.creator.domain.useCase.tags

import ru.ermakov.creator.domain.repository.TagRepository

class DeleteTagByIdUseCase(private val tagRepository: TagRepository) {
    suspend operator fun invoke(tagId: Long) {
        tagRepository.deleteTagById(tagId = tagId)
    }
}
