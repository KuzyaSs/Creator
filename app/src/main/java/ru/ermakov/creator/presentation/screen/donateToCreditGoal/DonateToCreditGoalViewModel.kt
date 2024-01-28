package ru.ermakov.creator.presentation.screen.donateToCreditGoal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.CreditGoalTransactionRequest
import ru.ermakov.creator.domain.useCase.donateToCreditGoal.InsertCreditGoalTransactionUseCase
import ru.ermakov.creator.domain.useCase.editCreditGoal.GetCreditGoalByIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetBalanceByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

private const val TRANSFER_TO_CREDIT_GOAL_TRANSACTION_TYPE_ID = 6L

class DonateToCreditGoalViewModel(
    private val insertCreditGoalTransactionUseCase: InsertCreditGoalTransactionUseCase,
    private val getCreditGoalByIdUseCase: GetCreditGoalByIdUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getBalanceByUserIdUseCase: GetBalanceByUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _donateToCreditGoalUiState = MutableLiveData(DonateToCreditGoalUiState())
    val donateToCreditGoalUiState: LiveData<DonateToCreditGoalUiState> = _donateToCreditGoalUiState

    fun refreshDonateToCreditGoalFragment(creditGoalId: Long) {
        _donateToCreditGoalUiState.value =
            _donateToCreditGoalUiState.value?.copy(isRefreshingShown = true)
        setDonateToCreditGoalFragment(creditGoalId = creditGoalId)
    }

    fun donate(creatorId: String, creditGoalId: Long, amount: Long, message: String) {
        _donateToCreditGoalUiState.postValue(
            _donateToCreditGoalUiState.value?.copy(
                isProgressBarDonateShown = true,
                isErrorMessageShown = false,
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val creditGoalTransactionRequest = CreditGoalTransactionRequest(
                    creditGoalId = creditGoalId,
                    senderUserId = getCurrentUserIdUseCase(),
                    receiverUserId = creatorId,
                    transactionTypeId = TRANSFER_TO_CREDIT_GOAL_TRANSACTION_TYPE_ID,
                    amount = amount,
                    message = message
                )
                insertCreditGoalTransactionUseCase(creditGoalTransactionRequest = creditGoalTransactionRequest)
                _donateToCreditGoalUiState.postValue(
                    _donateToCreditGoalUiState.value?.copy(
                        isDonated = true,
                        isProgressBarDonateShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _donateToCreditGoalUiState.postValue(
                    _donateToCreditGoalUiState.value?.copy(
                        isProgressBarDonateShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun setDonateToCreditGoalFragment(creditGoalId: Long) {
        _donateToCreditGoalUiState.value = _donateToCreditGoalUiState.value?.copy(
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _donateToCreditGoalUiState.postValue(
                    _donateToCreditGoalUiState.value?.copy(
                        creditGoal = getCreditGoalByIdUseCase(creditGoalId = creditGoalId),
                        balance = getBalanceByUserIdUseCase(userId = getCurrentUserIdUseCase()),
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _donateToCreditGoalUiState.postValue(
                    _donateToCreditGoalUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

}