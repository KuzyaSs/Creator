package ru.ermakov.creator.domain.useCase.createTag

import ru.ermakov.creator.domain.exception.EmptyDataException
import ru.ermakov.creator.domain.model.TagRequest
import ru.ermakov.creator.domain.repository.TagRepository

class InsertTagUseCase(private val tagRepository: TagRepository) {
    suspend operator fun invoke(tagRequest: TagRequest) {
        if (tagRequest.name.isBlank()) {
            throw EmptyDataException()
        }
        tagRepository.insertTag(tagRequest = tagRequest)
    }
}
