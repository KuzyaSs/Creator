package ru.ermakov.creator.presentation.screen.withdrawal

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
import ru.ermakov.creator.domain.useCase.topUp.ValidateCreditCardNumberUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

private const val WITHDRAWAL_TRANSACTION_TYPE_ID = 2L

class WithdrawalViewModel(
    private val insertUserTransactionUseCase: InsertUserTransactionUseCase,
    private val validateCreditCardNumberUseCase: ValidateCreditCardNumberUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getBalanceByUserIdUseCase: GetBalanceByUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _withdrawalUiState = MutableLiveData(WithdrawalUiState())
    val withdrawalUiState: LiveData<WithdrawalUiState> = _withdrawalUiState

    init {
        setWithdrawalFragment()
    }

    fun refreshWithdrawalFragment() {
        _withdrawalUiState.value = _withdrawalUiState.value?.copy(isRefreshingShown = true)
        setWithdrawalFragment()
    }

    fun withdraw(amount: Long, cardNumber: String) {
        _withdrawalUiState.postValue(
            _withdrawalUiState.value?.copy(
                isProgressBarWithdrawShown = true,
                isErrorMessageShown = false,
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                validateCreditCardNumberUseCase(cardNumber = cardNumber)
                val currentUserId = getCurrentUserIdUseCase()
                val userTransactionRequest = UserTransactionRequest(
                    senderUserId = currentUserId,
                    receiverUserId = currentUserId,
                    transactionTypeId = WITHDRAWAL_TRANSACTION_TYPE_ID,
                    amount = amount,
                    message = ""
                )
                insertUserTransactionUseCase(userTransactionRequest = userTransactionRequest)
                _withdrawalUiState.postValue(
                    _withdrawalUiState.value?.copy(
                        isWithdrawn = true,
                        isProgressBarWithdrawShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _withdrawalUiState.postValue(
                    _withdrawalUiState.value?.copy(
                        isProgressBarWithdrawShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    private fun setWithdrawalFragment() {
        _withdrawalUiState.value = _withdrawalUiState.value?.copy(
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _withdrawalUiState.postValue(
                    _withdrawalUiState.value?.copy(
                        balance = getBalanceByUserIdUseCase(userId = getCurrentUserIdUseCase()),
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _withdrawalUiState.postValue(
                    _withdrawalUiState.value?.copy(
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