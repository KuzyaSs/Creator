package ru.ermakov.creator.domain.useCase.chooseUserCategory

import android.util.Log
import ru.ermakov.creator.domain.exception.UnexpectedValueException
import ru.ermakov.creator.domain.model.UserCategory
import ru.ermakov.creator.domain.repository.CategoryRepository

class UpdateUserCategoriesUseCase(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(userId: String, userCategories: List<UserCategory>?) {
        if (userCategories == null) {
            throw UnexpectedValueException()
        }
        categoryRepository.updateUserCategories(userId = userId, userCategories = userCategories)
        Log.d("MY_TAG", "UpdateUserCategoriesUseCase $userId: $userCategories")
    }
}