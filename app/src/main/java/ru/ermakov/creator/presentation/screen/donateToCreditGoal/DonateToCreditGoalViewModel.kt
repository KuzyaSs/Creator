package ru.ermakov.creator.presentation.screen.donateToCreditGoal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.UserTransactionRequest
import ru.ermakov.creator.domain.useCase.shared.GetBalanceByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.InsertUserTransactionUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

private const val TRANSFER_TO_USER_TRANSACTION_TYPE_ID = 5L

class DonateToCreditGoalViewModel(
    private val insertUserTransactionUseCase: InsertUserTransactionUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getBalanceByUserIdUseCase: GetBalanceByUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _donateToCreditGoalUiState = MutableLiveData(DonateToCreditGoalUiState())
    val donateToCreditGoalUiState: LiveData<DonateToCreditGoalUiState> = _donateToCreditGoalUiState

    init {
        setTipFragment()
    }

    fun refreshTipFragment() {
        _donateToCreditGoalUiState.value = _donateToCreditGoalUiState.value?.copy(isRefreshingShown = true)
        setTipFragment()
    }

    fun sendTip(creatorId: String, amount: Long, message: String) {
        _donateToCreditGoalUiState.postValue(
            _donateToCreditGoalUiState.value?.copy(
                isProgressBarDonateShown = true,
                isErrorMessageShown = false,
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userTransactionRequest = UserTransactionRequest(
                    senderUserId = getCurrentUserIdUseCase(),
                    receiverUserId = creatorId,
                    transactionTypeId = TRANSFER_TO_USER_TRANSACTION_TYPE_ID,
                    amount = amount,
                    message = message
                )
                insertUserTransactionUseCase(userTransactionRequest = userTransactionRequest)
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

    private fun setTipFragment() {
        _donateToCreditGoalUiState.value = _donateToCreditGoalUiState.value?.copy(
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _donateToCreditGoalUiState.postValue(
                    _donateToCreditGoalUiState.value?.copy(
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