package ru.ermakov.creator.domain.useCase.chooseUserCategory

import ru.ermakov.creator.domain.exception.UnexpectedValueException
import ru.ermakov.creator.domain.model.UserCategory

class UpdateUserCategoryInListUseCase {
    operator fun invoke(
        userCategories: List<UserCategory>?,
        changedUserCategory: UserCategory
    ): List<UserCategory> {
        if (userCategories == null) {
            throw UnexpectedValueException()
        }
        val updatedUserCategories = userCategories.map { userCategory ->
            if (userCategory.id == changedUserCategory.id) {
                changedUserCategory
            } else {
                userCategory
            }
        }
        return updatedUserCategories
    }
}