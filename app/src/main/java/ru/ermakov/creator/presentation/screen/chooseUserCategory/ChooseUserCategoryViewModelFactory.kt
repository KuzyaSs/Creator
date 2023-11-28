package ru.ermakov.creator.presentation.screen.chooseUserCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.chooseUserCategory.UpdateUserCategoriesUseCase
import ru.ermakov.creator.domain.useCase.chooseUserCategory.UpdateUserCategoryInListUseCase
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.common.GetUserCategoriesUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler


class ChooseUserCategoryViewModelFactory(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserCategoriesUseCase: GetUserCategoriesUseCase,
    private val updateUserCategoriesUseCase: UpdateUserCategoriesUseCase,
    private val updateUserCategoryInListUseCase: UpdateUserCategoryInListUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChooseUserCategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChooseUserCategoryViewModel(
                getCurrentUserUseCase = getCurrentUserUseCase,
                getUserCategoriesUseCase = getUserCategoriesUseCase,
                updateUserCategoriesUseCase = updateUserCategoriesUseCase,
                updateUserCategoryInListUseCase = updateUserCategoryInListUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw Exception("Unknown ViewModel class")
    }
}