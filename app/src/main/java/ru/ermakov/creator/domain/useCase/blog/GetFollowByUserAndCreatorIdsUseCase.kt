package ru.ermakov.creator.domain.useCase.blog

import ru.ermakov.creator.domain.model.Follow
import ru.ermakov.creator.domain.repository.FollowRepository

class GetFollowByUserAndCreatorIdsUseCase(private val followRepository: FollowRepository) {
    suspend operator fun invoke(userId: String, creatorId: String): Follow {
        return followRepository.getFollowByUserAndCreatorIds(userId = userId, creatorId = creatorId)
    }
}