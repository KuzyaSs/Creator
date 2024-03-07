package ru.ermakov.creator.domain.useCase.postComments

import ru.ermakov.creator.domain.repository.PostCommentRepository

class DeletePostCommentByIdUseCase(private val postCommentRepository: PostCommentRepository) {
    suspend operator fun invoke(postCommentId: Long) {
        return postCommentRepository.deletePostCommentById(postCommentId = postCommentId)
    }
}