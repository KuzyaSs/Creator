package ru.ermakov.creator.domain.useCase.blog

import ru.ermakov.creator.domain.exception.UnexpectedValueException
import ru.ermakov.creator.domain.repository.FollowRepository

class UnfollowUseCase(
    private val getFollowByUserAndCreatorIdsUseCase: GetFollowByUserAndCreatorIdsUseCase,
    private val followRepository: FollowRepository
) {
    suspend operator fun invoke(userId: String?, creatorId: String?) {
        if (userId.isNullOrEmpty() || creatorId.isNullOrEmpty()) {
            throw UnexpectedValueException()
        }
        val follow = getFollowByUserAndCreatorIdsUseCase(userId = userId, creatorId = creatorId)
        followRepository.deleteFollowById(followId = follow.id)
    }
}