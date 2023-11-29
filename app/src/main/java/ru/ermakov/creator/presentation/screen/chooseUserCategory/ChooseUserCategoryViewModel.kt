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
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserIdUseCase
import ru.ermakov.creator.domain.useCase.common.GetUserCategoriesUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class ChooseUserCategoryViewModel(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
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
                isErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updateUserCategoriesUseCase(
                    userId = getCurrentUserIdUseCase(),
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
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
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

    fun clearErrorMessage() {
        _chooseUserCategoryUiState.value = _chooseUserCategoryUiState.value?.copy(
            isErrorMessageShown = false,
            errorMessage = ""
        )
    }

    private fun setUserCategories() {
        _chooseUserCategoryUiState.postValue(
            _chooseUserCategoryUiState.value?.copy(
                isErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val categories = getUserCategoriesUseCase(userId = getCurrentUserIdUseCase())
                _chooseUserCategoryUiState.postValue(
                    _chooseUserCategoryUiState.value?.copy(
                        userCategories = categories,
                        isRefreshingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _chooseUserCategoryUiState.postValue(
                    _chooseUserCategoryUiState.value?.copy(
                        isRefreshingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage,
                    )
                )
            }
        }
    }
}