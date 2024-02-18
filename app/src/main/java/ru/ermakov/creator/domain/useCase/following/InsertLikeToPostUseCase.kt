package ru.ermakov.creator.domain.useCase.following

import ru.ermakov.creator.domain.exception.UnexpectedValueException
import ru.ermakov.creator.domain.model.LikeRequest
import ru.ermakov.creator.domain.repository.PostRepository

class InsertLikeToPostUseCase(private val postRepository: PostRepository) {
    suspend operator fun invoke(userId: String?, postId: Long) {
        if (userId == null) {
            throw UnexpectedValueException()
        }

        val likeRequest = LikeRequest(userId = userId, postId = postId)
        postRepository.insertLikeToPost(likeRequest = likeRequest)
    }
}
