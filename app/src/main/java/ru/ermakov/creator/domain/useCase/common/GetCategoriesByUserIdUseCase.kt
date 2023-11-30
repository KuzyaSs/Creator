package ru.ermakov.creator.domain.useCase.common

import ru.ermakov.creator.domain.model.Category
import ru.ermakov.creator.domain.repository.CategoryRepository

class GetCategoriesByUserIdUseCase(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(userId: String): List<Category> {
        return categoryRepository.getCategoriesByUserId(userId = userId)
    }
}