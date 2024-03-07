package ru.ermakov.creator.domain.useCase.postComments

import ru.ermakov.creator.domain.repository.PostCommentRepository

class DeletePostCommentLikeUseCase(private val postCommentRepository: PostCommentRepository) {
    suspend operator fun invoke(postCommentId: Long, userId: String) {
        return postCommentRepository.deletePostCommentLike(
            postCommentId = postCommentId,
            userId = userId
        )
    }
}