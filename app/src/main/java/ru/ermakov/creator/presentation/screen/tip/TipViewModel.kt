package ru.ermakov.creator.presentation.screen.tip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.UserTransactionRequest
import ru.ermakov.creator.domain.useCase.shared.GetBalanceByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.domain.useCase.tip.SendTipUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

private const val TRANSFER_TO_USER_TRANSACTION_TYPE_ID = 5L

class TipViewModel(
    private val sendTipUseCase: SendTipUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getBalanceByUserIdUseCase: GetBalanceByUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _tipUiState = MutableLiveData(TipUiState())
    val tipUiState: LiveData<TipUiState> = _tipUiState

    init {
        setTipFragment()
    }

    fun refreshTipFragment() {
        _tipUiState.value = _tipUiState.value?.copy(isRefreshingShown = true)
        setTipFragment()
    }

    fun sendTip(creatorId: String, amount: Long, message: String) {
        _tipUiState.postValue(
            _tipUiState.value?.copy(
                isProgressBarSendTipShown = true,
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
                sendTipUseCase(userTransactionRequest = userTransactionRequest)
                _tipUiState.postValue(
                    _tipUiState.value?.copy(
                        isTipSent = true,
                        isProgressBarSendTipShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _tipUiState.postValue(
                    _tipUiState.value?.copy(
                        isProgressBarSendTipShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    private fun setTipFragment() {
        _tipUiState.value = _tipUiState.value?.copy(
            isLoading = true,
            isErrorMessageShown = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _tipUiState.postValue(
                    _tipUiState.value?.copy(
                        balance = getBalanceByUserIdUseCase(userId = getCurrentUserIdUseCase()),
                        isRefreshingShown = false,
                        isLoading = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _tipUiState.postValue(
                    _tipUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoading = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

}