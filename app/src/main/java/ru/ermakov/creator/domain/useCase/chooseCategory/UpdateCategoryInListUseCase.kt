package ru.ermakov.creator.domain.useCase.chooseCategory

import ru.ermakov.creator.domain.exception.UnexpectedValueException
import ru.ermakov.creator.domain.model.Category

class UpdateCategoryInListUseCase {
    operator fun invoke(
        categories: List<Category>?,
        changedCategory: Category
    ): List<Category> {
        if (categories == null) {
            throw UnexpectedValueException()
        }
        val updatedCategories = categories.map { category ->
            if (category.id == changedCategory.id) {
                changedCategory
            } else {
                category
            }
        }
        return updatedCategories
    }
}