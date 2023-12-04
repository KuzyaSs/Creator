package ru.ermakov.creator.domain.useCase.blog

import ru.ermakov.creator.domain.repository.FollowRepository

class GetFollowerCountByUserIdUseCase(private val followRepository: FollowRepository) {
    suspend operator fun invoke(userId: String): Long {
        return followRepository.getFollowerCountByUserId(userId = userId)
    }
}