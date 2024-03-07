package ru.ermakov.creator.domain.useCase.postComments

import ru.ermakov.creator.domain.model.PostCommentLikeRequest
import ru.ermakov.creator.domain.repository.PostCommentRepository

class InsertPostCommentLikeUseCase(private val postCommentRepository: PostCommentRepository) {
    suspend operator fun invoke(postCommentLikeRequest: PostCommentLikeRequest) {
        postCommentRepository.insertPostCommentLike(
            postCommentLikeRequest = postCommentLikeRequest
        )
    }
}