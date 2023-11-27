package ru.ermakov.creator.presentation.screen.chooseUserCategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.UserCategory
import ru.ermakov.creator.domain.useCase.chooseUserCategory.UpdateUserCategoriesUseCase
import ru.ermakov.creator.domain.useCase.chooseUserCategory.UpdateUserCategoryInListUseCase
import ru.ermakov.creator.domain.useCase.common.GetUserCategoriesUseCase
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserUseCase
import ru.ermakov.creator.presentation.exception.ExceptionHandler

class ChooseUserCategoryViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserCategoriesUseCase: GetUserCategoriesUseCase,
    private val updateUserCategoriesUseCase: UpdateUserCategoriesUseCase,
    private val updateUserCategoryInListUseCase: UpdateUserCategoryInListUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _chooseUserCategoryUiState = MutableLiveData(ChooseUserCategoryUiState())
    val chooseUserCategoryUiState: LiveData<ChooseUserCategoryUiState> = _chooseUserCategoryUiState

    init {
        setUserCategories()
    }

    fun updateUserCategories() {
        _chooseUserCategoryUiState.postValue(
            _chooseUserCategoryUiState.value?.copy(
                isProgressBarConfirmShown = true,
                isChooseCategoryErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateUserCategoriesUseCase(
                    userId = getCurrentUserUseCase().id,
                    userCategories = _chooseUserCategoryUiState.value?.userCategories
                )
                _chooseUserCategoryUiState.postValue(
                    _chooseUserCategoryUiState.value?.copy(
                        isProgressBarConfirmShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _chooseUserCategoryUiState.postValue(
                    _chooseUserCategoryUiState.value?.copy(
                        isProgressBarConfirmShown = false,
                        isChooseCategoryErrorMessageShown = true,
                        chooseCategoryErrorMessage = errorMessage,
                    )
                )
            }
        }
    }

    fun refreshUserCategories() {
        _chooseUserCategoryUiState.value = _chooseUserCategoryUiState.value?.copy(
            isRefreshingShown = true
        )
        setUserCategories()
    }

    fun updateUserCategoryInList(changedUserCategory: UserCategory) {
        val updatedCategories = updateUserCategoryInListUseCase(
            userCategories = _chooseUserCategoryUiState.value?.userCategories,
            changedUserCategory = changedUserCategory
        )
        _chooseUserCategoryUiState.value = _chooseUserCategoryUiState.value?.copy(
            userCategories = updatedCategories
        )
    }

    fun clearChooseCategoryErrorMessage() {
        _chooseUserCategoryUiState.value = _chooseUserCategoryUiState.value?.copy(
            isChooseCategoryErrorMessageShown = false,
            chooseCategoryErrorMessage = ""
        )
    }

    private fun setUserCategories() {
        _chooseUserCategoryUiState.postValue(
            _chooseUserCategoryUiState.value?.copy(
                isChooseCategoryErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val categories = getUserCategoriesUseCase(userId = getCurrentUserUseCase().id)
                _chooseUserCategoryUiState.postValue(
                    _chooseUserCategoryUiState.value?.copy(
                        userCategories = categories,
                        isRefreshingShown = false,
                        isChooseCategoryErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _chooseUserCategoryUiState.postValue(
                    _chooseUserCategoryUiState.value?.copy(
                        isRefreshingShown = false,
                        isChooseCategoryErrorMessageShown = true,
                        chooseCategoryErrorMessage = errorMessage,
                    )
                )
            }
        }
    }
}