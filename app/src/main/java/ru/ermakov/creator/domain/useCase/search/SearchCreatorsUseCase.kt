package ru.ermakov.creator.domain.useCase.search

import ru.ermakov.creator.domain.model.Creator
import ru.ermakov.creator.domain.repository.CreatorRepository

class SearchCreatorsUseCase(private val creatorRepository: CreatorRepository) {
    suspend operator fun invoke(searchQuery: String, page: Int): List<Creator> {
        return creatorRepository.getCreatorsByPage(searchQuery = searchQuery, page = page)
    }
}