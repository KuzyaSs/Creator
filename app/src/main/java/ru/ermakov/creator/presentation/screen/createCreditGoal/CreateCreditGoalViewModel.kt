package ru.ermakov.creator.presentation.screen.createCreditGoal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.CreditGoalRequest
import ru.ermakov.creator.domain.useCase.createCreditGoal.CreateCreditGoalUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class CreateCreditGoalViewModel(
    private val createCreditGoalUseCase: CreateCreditGoalUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) :
    ViewModel() {
    private val _createCreditGoalUiState = MutableLiveData(CreateCreditGoalUiState())
    val createCreditGoalUiState: LiveData<CreateCreditGoalUiState> = _createCreditGoalUiState

    fun createCreditGoal(targetBalance: Long, description: String) {
        _createCreditGoalUiState.postValue(
            _createCreditGoalUiState.value?.copy(
                isProgressBarCreateShown = true,
                isErrorMessageShown = false,
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val creditGoalRequest = CreditGoalRequest(
                    creatorId = getCurrentUserIdUseCase(),
                    targetBalance = targetBalance,
                    description = description
                )
                createCreditGoalUseCase(creditGoalRequest = creditGoalRequest)
                _createCreditGoalUiState.postValue(
                    _createCreditGoalUiState.value?.copy(
                        isCreditGoalCreated = true,
                        isProgressBarCreateShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _createCreditGoalUiState.postValue(
                    _createCreditGoalUiState.value?.copy(
                        isProgressBarCreateShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }
}