package ru.ermakov.creator.domain.useCase.blog

import ru.ermakov.creator.domain.exception.UnexpectedValueException
import ru.ermakov.creator.domain.repository.FollowRepository

class FollowUseCase(private val followRepository: FollowRepository) {
    suspend operator fun invoke(userId: String?, creatorId: String?) {
        if (userId.isNullOrEmpty() || creatorId.isNullOrEmpty()) {
            throw UnexpectedValueException()
        }
        followRepository.insertFollow(userId = userId, creatorId = creatorId)
    }
}