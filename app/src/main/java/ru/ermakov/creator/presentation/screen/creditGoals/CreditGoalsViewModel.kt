package ru.ermakov.creator.presentation.screen.creditGoals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ermakov.creator.domain.model.CreditGoal
import ru.ermakov.creator.domain.useCase.creditGoals.CloseCreditGoalUseCase
import ru.ermakov.creator.domain.useCase.creditGoals.GetCreditGoalsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class CreditGoalsViewModel(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getCreditGoalsByCreatorIdUseCase: GetCreditGoalsByCreatorIdUseCase,
    private val closeCreditGoalUseCase: CloseCreditGoalUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _creditGoalsUiState = MutableLiveData(CreditGoalsUiState())
    val creditGoalsUiState: LiveData<CreditGoalsUiState> = _creditGoalsUiState

    fun setCreditGoals(creatorId: String) {
        _creditGoalsUiState.postValue(
            _creditGoalsUiState.value?.copy(
                isLoadingShown = true,
                isErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUserId = getCurrentUserIdUseCase()
                _creditGoalsUiState.postValue(
                    _creditGoalsUiState.value?.copy(
                        currentUserId = currentUserId,
                        creditGoals = getCreditGoalsByCreatorIdUseCase(creatorId = creatorId),
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _creditGoalsUiState.postValue(
                    _creditGoalsUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun refreshCreditGoals(creatorId: String) {
        _creditGoalsUiState.value = _creditGoalsUiState.value?.copy(
            isRefreshingShown = true
        )
        setCreditGoals(creatorId = creatorId)
    }

    fun closeSelectedCreditGoal(creatorId: String) {
        _creditGoalsUiState.postValue(
            _creditGoalsUiState.value?.copy(
                isRefreshingShown = true,
                isErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val selectedCreditGoalId = _creditGoalsUiState.value?.selectedCreditGoal
                closeCreditGoalUseCase(
                    creditGoal = selectedCreditGoalId,
                    senderUserId = getCurrentUserIdUseCase(),
                    creatorId = creatorId
                )
                withContext(Dispatchers.Main) {
                    refreshCreditGoals(creatorId = creatorId)
                }
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _creditGoalsUiState.postValue(
                    _creditGoalsUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun setSelectedCreditGoal(creditGoal: CreditGoal) {
        _creditGoalsUiState.value = _creditGoalsUiState.value?.copy(
            selectedCreditGoal = creditGoal
        )
    }

    fun clearErrorMessage() {
        _creditGoalsUiState.value = _creditGoalsUiState.value?.copy(
            isErrorMessageShown = false,
            errorMessage = ""
        )
    }
}