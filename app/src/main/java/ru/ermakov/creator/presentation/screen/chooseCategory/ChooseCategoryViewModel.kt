package ru.ermakov.creator.presentation.screen.chooseCategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.Category
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoriesUseCase
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoryInListUseCase
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserIdUseCase
import ru.ermakov.creator.domain.useCase.common.GetCategoriesByUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class ChooseCategoryViewModel(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getCategoriesByUserIdUseCase: GetCategoriesByUserIdUseCase,
    private val updateCategoriesUseCase: UpdateCategoriesUseCase,
    private val updateCategoryInListUseCase: UpdateCategoryInListUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _chooseCategoryUiState = MutableLiveData(ChooseCategoryUiState())
    val chooseCategoryUiState: LiveData<ChooseCategoryUiState> = _chooseCategoryUiState

    init {
        setCategories()
    }

    fun updateCategories() {
        _chooseCategoryUiState.postValue(
            _chooseCategoryUiState.value?.copy(
                isProgressBarConfirmShown = true,
                isErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateCategoriesUseCase(
                    userId = getCurrentUserIdUseCase(),
                    categories = _chooseCategoryUiState.value?.categories
                )
                _chooseCategoryUiState.postValue(
                    _chooseCategoryUiState.value?.copy(
                        isProgressBarConfirmShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _chooseCategoryUiState.postValue(
                    _chooseCategoryUiState.value?.copy(
                        isProgressBarConfirmShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun refreshCategories() {
        _chooseCategoryUiState.value = _chooseCategoryUiState.value?.copy(
            isRefreshingShown = true
        )
        setCategories()
    }

    fun updateCategoryInList(changedCategory: Category) {
        val updatedCategories = updateCategoryInListUseCase(
            categories = _chooseCategoryUiState.value?.categories,
            changedCategory = changedCategory
        )
        _chooseCategoryUiState.value = _chooseCategoryUiState.value?.copy(
            categories = updatedCategories
        )
    }

    fun clearErrorMessage() {
        _chooseCategoryUiState.value = _chooseCategoryUiState.value?.copy(
            isErrorMessageShown = false,
            errorMessage = ""
        )
    }

    private fun setCategories() {
        _chooseCategoryUiState.postValue(
            _chooseCategoryUiState.value?.copy(
                isErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val categories = getCategoriesByUserIdUseCase(userId = getCurrentUserIdUseCase())
                _chooseCategoryUiState.postValue(
                    _chooseCategoryUiState.value?.copy(
                        categories = categories,
                        isRefreshingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _chooseCategoryUiState.postValue(
                    _chooseCategoryUiState.value?.copy(
                        isRefreshingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage,
                    )
                )
            }
        }
    }
}