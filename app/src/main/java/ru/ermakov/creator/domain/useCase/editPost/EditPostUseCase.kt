package ru.ermakov.creator.domain.useCase.editPost

import ru.ermakov.creator.domain.model.PostRequest
import ru.ermakov.creator.domain.repository.PostRepository

class EditPostUseCase(private val postRepository: PostRepository) {
    suspend operator fun invoke(postId: Long, postRequest: PostRequest) {
        return postRepository.updatePost(postId = postId, postRequest = postRequest)
    }
}
