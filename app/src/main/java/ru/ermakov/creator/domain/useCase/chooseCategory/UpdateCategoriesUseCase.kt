package ru.ermakov.creator.domain.useCase.chooseCategory

import android.util.Log
import ru.ermakov.creator.domain.exception.UnexpectedValueException
import ru.ermakov.creator.domain.model.Category
import ru.ermakov.creator.domain.repository.CategoryRepository

class UpdateCategoriesUseCase(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(userId: String, categories: List<Category>?) {
        if (categories == null) {
            throw UnexpectedValueException()
        }
        categoryRepository.updateCategories(userId = userId, categories = categories)
    }
}