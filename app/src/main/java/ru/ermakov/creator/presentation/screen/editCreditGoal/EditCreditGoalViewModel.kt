package ru.ermakov.creator.presentation.screen.editCreditGoal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.CreditGoalRequest
import ru.ermakov.creator.domain.useCase.editCreditGoal.EditCreditGoalUseCase
import ru.ermakov.creator.domain.useCase.editCreditGoal.GetCreditGoalByIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class EditCreditGoalViewModel(
    private val getCreditGoalByIdUseCase: GetCreditGoalByIdUseCase,
    private val editCreditGoalUseCase: EditCreditGoalUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) :
    ViewModel() {
    private val _editCreditGoalUiState = MutableLiveData(EditCreditGoalUiState())
    val editCreditGoalUiState: LiveData<EditCreditGoalUiState> = _editCreditGoalUiState

    fun setCreditGoal(creditGoalId: Long) {
        _editCreditGoalUiState.value = _editCreditGoalUiState.value?.copy(
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _editCreditGoalUiState.postValue(
                    _editCreditGoalUiState.value?.copy(
                        creditGoal = getCreditGoalByIdUseCase(creditGoalId = creditGoalId),
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _editCreditGoalUiState.postValue(
                    _editCreditGoalUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun refreshCreditGoal(creditGoalId: Long) {
        _editCreditGoalUiState.value = _editCreditGoalUiState.value?.copy(
            isRefreshingShown = true
        )
        setCreditGoal(creditGoalId = creditGoalId)
    }

    fun editSubscription(creditGoalId: Long, targetBalance: Long, description: String) {
        _editCreditGoalUiState.postValue(
            _editCreditGoalUiState.value?.copy(
                isProgressBarSaveChangesShown = true,
                isErrorMessageShown = false,
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val creditGoalRequest = CreditGoalRequest(
                    creatorId = getCurrentUserIdUseCase(),
                    targetBalance = targetBalance,
                    description = description,
                )
                editCreditGoalUseCase(
                    creditGoalId = creditGoalId,
                    creditGoalRequest = creditGoalRequest
                )
                _editCreditGoalUiState.postValue(
                    _editCreditGoalUiState.value?.copy(
                        isCreditGoalEdited = true,
                        isProgressBarSaveChangesShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _editCreditGoalUiState.postValue(
                    _editCreditGoalUiState.value?.copy(
                        isProgressBarSaveChangesShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }
}