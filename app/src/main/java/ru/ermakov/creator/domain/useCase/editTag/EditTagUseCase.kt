package ru.ermakov.creator.domain.useCase.editTag

import ru.ermakov.creator.domain.exception.EmptyDataException
import ru.ermakov.creator.domain.model.TagRequest
import ru.ermakov.creator.domain.repository.TagRepository

class EditTagUseCase(private val tagRepository: TagRepository) {
    suspend operator fun invoke(tagId: Long, tagRequest: TagRequest) {
        if (tagRequest.name.isBlank()) {
            throw EmptyDataException()
        }
        tagRepository.updateTag(tagId = tagId, tagRequest = tagRequest)
    }
}