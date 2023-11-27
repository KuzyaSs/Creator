package ru.ermakov.creator.domain.useCase.common

import ru.ermakov.creator.domain.model.UserCategory
import ru.ermakov.creator.domain.repository.CategoryRepository

class GetUserCategoriesUseCase(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(userId: String): List<UserCategory> {
        return categoryRepository.getUserCategoriesByUserId(userId = userId)
    }
}