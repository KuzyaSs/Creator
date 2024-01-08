package ru.ermakov.creator.domain.useCase.follows

import ru.ermakov.creator.domain.model.Follow
import ru.ermakov.creator.domain.repository.FollowRepository

class SearchFollowPageByUserIdUseCase(private val followRepository: FollowRepository) {
    suspend operator fun invoke(
        searchQuery: String,
        page: Int,
        userId: String
    ): List<Follow> {
        return followRepository.getFollowPageByUserId(
            searchQuery = searchQuery,
            page = page,
            userId = userId
        )
    }
}