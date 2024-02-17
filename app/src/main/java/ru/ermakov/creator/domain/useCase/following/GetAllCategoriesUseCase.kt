package ru.ermakov.creator.domain.useCase.following

import ru.ermakov.creator.domain.model.Category
import ru.ermakov.creator.domain.repository.CategoryRepository

class GetAllCategoriesUseCase(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(): List<Category> {
        return categoryRepository.getAllCategories()
    }
}
