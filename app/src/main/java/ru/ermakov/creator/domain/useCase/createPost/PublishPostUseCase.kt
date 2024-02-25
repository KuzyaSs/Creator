package ru.ermakov.creator.domain.useCase.createPost

import ru.ermakov.creator.domain.exception.EmptyDataException
import ru.ermakov.creator.domain.model.PostRequest
import ru.ermakov.creator.domain.repository.PostRepository

class PublishPostUseCase(private val postRepository: PostRepository) {
    suspend operator fun invoke(postRequest: PostRequest) {
        if (postRequest.title.isBlank() || postRequest.content.isBlank()) {
            throw EmptyDataException()
        }
        postRepository.insertPost(postRequest = postRequest)
    }
}
