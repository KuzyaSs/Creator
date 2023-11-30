package ru.ermakov.creator.presentation.screen.chooseCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoriesUseCase
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoryInListUseCase
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserIdUseCase
import ru.ermakov.creator.domain.useCase.common.GetCategoriesByUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler


class ChooseCategoryViewModelFactory(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getCategoriesByUserIdUseCase: GetCategoriesByUserIdUseCase,
    private val updateCategoriesUseCase: UpdateCategoriesUseCase,
    private val updateCategoryInListUseCase: UpdateCategoryInListUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChooseCategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChooseCategoryViewModel(
                getCurrentUserIdUseCase = getCurrentUserIdUseCase,
                getCategoriesByUserIdUseCase = getCategoriesByUserIdUseCase,
                updateCategoriesUseCase = updateCategoriesUseCase,
                updateCategoryInListUseCase = updateCategoryInListUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw Exception("Unknown ViewModel class")
    }
}