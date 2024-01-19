package ru.ermakov.creator.presentation.screen.topUp

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
import ru.ermakov.creator.domain.useCase.topUp.ValidateCreditCardDetailsUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

private const val TOP_UP_TRANSACTION_TYPE_ID = 1L

class TopUpViewModel(
    private val insertUserTransactionUseCase: InsertUserTransactionUseCase,
    private val validateCreditCardDetailsUseCase: ValidateCreditCardDetailsUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getBalanceByUserIdUseCase: GetBalanceByUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _topUpUiState = MutableLiveData(TopUpUiState())
    val topUpUiState: LiveData<TopUpUiState> = _topUpUiState

    init {
        setTopUpFragment()
    }

    fun refreshTopUpFragment() {
        _topUpUiState.value = _topUpUiState.value?.copy(isRefreshingShown = true)
        setTopUpFragment()
    }

    fun topUp(amount: Long, cardNumber: String, validity: String, cvv: String) {
        _topUpUiState.postValue(
            _topUpUiState.value?.copy(
                isProgressBarTopUpShown = true,
                isErrorMessageShown = false,
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                validateCreditCardDetailsUseCase(cardNumber, validity, cvv)
                val currentUserId = getCurrentUserIdUseCase()
                val userTransactionRequest = UserTransactionRequest(
                    senderUserId = currentUserId,
                    receiverUserId = currentUserId,
                    transactionTypeId = TOP_UP_TRANSACTION_TYPE_ID,
                    amount = amount,
                    message = ""
                )
                insertUserTransactionUseCase(userTransactionRequest = userTransactionRequest)
                _topUpUiState.postValue(
                    _topUpUiState.value?.copy(
                        isToppedUp = true,
                        isProgressBarTopUpShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _topUpUiState.postValue(
                    _topUpUiState.value?.copy(
                        isProgressBarTopUpShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    private fun setTopUpFragment() {
        _topUpUiState.value = _topUpUiState.value?.copy(
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _topUpUiState.postValue(
                    _topUpUiState.value?.copy(
                        balance = getBalanceByUserIdUseCase(userId = getCurrentUserIdUseCase()),
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _topUpUiState.postValue(
                    _topUpUiState.value?.copy(
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