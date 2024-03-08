package ru.ermakov.creator.domain.useCase.postComments

import ru.ermakov.creator.domain.exception.EmptyDataException
import ru.ermakov.creator.domain.model.PostCommentRequest
import ru.ermakov.creator.domain.repository.PostCommentRepository

class UpdatePostCommentUseCase(private val postCommentRepository: PostCommentRepository) {
    suspend operator fun invoke(postCommentId: Long, postCommentRequest: PostCommentRequest) {
        if (postCommentRequest.content.isBlank()) {
            throw EmptyDataException()
        }
        postCommentRepository.updatePostComment(
            postCommentId = postCommentId,
            postCommentRequest = postCommentRequest
        )
    }
}