package ru.ermakov.creator.domain.useCase.blog

import ru.ermakov.creator.data.exception.FollowNotFoundException
import ru.ermakov.creator.domain.repository.FollowRepository

class IsFollowerByUserAndCreatorIdsUseCase(private val followRepository: FollowRepository) {
    suspend operator fun invoke(userId: String, creatorId: String): Boolean {
        return try {
            followRepository.getFollowByUserAndCreatorIds(userId = userId, creatorId = creatorId)
            true
        } catch (followNotFoundException: FollowNotFoundException) {
            false
        }
    }
}