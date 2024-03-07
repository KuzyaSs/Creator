package ru.ermakov.creator.domain.useCase.postComments

import ru.ermakov.creator.domain.model.PostCommentRequest
import ru.ermakov.creator.domain.repository.PostCommentRepository

class InsertPostCommentUseCase(private val postCommentRepository: PostCommentRepository) {
    suspend operator fun invoke(postCommentRequest: PostCommentRequest) {
        postCommentRepository.insertPostComment(postCommentRequest = postCommentRequest)
    }
}