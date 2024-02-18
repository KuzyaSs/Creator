package ru.ermakov.creator.domain.useCase.following

import ru.ermakov.creator.domain.repository.PostRepository

class DeletePostByIdUseCase(private val postRepository: PostRepository) {
    suspend operator fun invoke(postId: Long) {
        postRepository.deletePostById(postId = postId)
    }
}
