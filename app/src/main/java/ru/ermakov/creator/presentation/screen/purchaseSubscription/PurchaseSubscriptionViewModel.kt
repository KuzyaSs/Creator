package ru.ermakov.creator.presentation.screen.purchaseSubscription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.UserSubscriptionRequest
import ru.ermakov.creator.domain.useCase.editSubscription.GetSubscriptionByIdUseCase
import ru.ermakov.creator.domain.useCase.purchaseSubscription.PurchaseSubscriptionUseCase
import ru.ermakov.creator.domain.useCase.shared.GetBalanceByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class PurchaseSubscriptionViewModel(
    private val getSubscriptionByIdUseCase: GetSubscriptionByIdUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getBalanceByUserIdUseCase: GetBalanceByUserIdUseCase,
    private val purchaseSubscriptionUseCase: PurchaseSubscriptionUseCase,
    private val exceptionHandler: ExceptionHandler
) :
    ViewModel() {
    private val _purchaseSubscriptionUiState = MutableLiveData(PurchaseSubscriptionUiState())
    val purchaseSubscriptionUiState: LiveData<PurchaseSubscriptionUiState> =
        _purchaseSubscriptionUiState

    fun setPurchaseSubscription(subscriptionId: Long) {
        _purchaseSubscriptionUiState.value = _purchaseSubscriptionUiState.value?.copy(
            isLoading = true,
            isErrorMessageShown = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _purchaseSubscriptionUiState.postValue(
                    _purchaseSubscriptionUiState.value?.copy(
                        subscription = getSubscriptionByIdUseCase(subscriptionId = subscriptionId),
                        balance = getBalanceByUserIdUseCase(userId = getCurrentUserIdUseCase()),
                        isRefreshingShown = false,
                        isLoading = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _purchaseSubscriptionUiState.postValue(
                    _purchaseSubscriptionUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoading = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun refreshPurchaseSubscription(subscriptionId: Long) {
        _purchaseSubscriptionUiState.value = _purchaseSubscriptionUiState.value?.copy(
            isRefreshingShown = true
        )
        setPurchaseSubscription(subscriptionId = subscriptionId)
    }

    fun purchaseSubscription(subscriptionId: Long, durationInMonths: Int) {
        _purchaseSubscriptionUiState.postValue(
            _purchaseSubscriptionUiState.value?.copy(
                isProgressBarPurchaseSubscriptionShown = true,
                isErrorMessageShown = false,
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userSubscriptionRequest = UserSubscriptionRequest(
                    subscriptionId = subscriptionId,
                    userId = getCurrentUserIdUseCase(),
                    durationInMonths = durationInMonths
                )
                purchaseSubscriptionUseCase(userSubscriptionRequest = userSubscriptionRequest)
                _purchaseSubscriptionUiState.postValue(
                    _purchaseSubscriptionUiState.value?.copy(
                        isSubscriptionPurchased = true,
                        isProgressBarPurchaseSubscriptionShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _purchaseSubscriptionUiState.postValue(
                    _purchaseSubscriptionUiState.value?.copy(
                        isProgressBarPurchaseSubscriptionShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun setSelectedSubscriptionPeriod(durationInMonths: Int) {
        _purchaseSubscriptionUiState.value = _purchaseSubscriptionUiState.value?.copy(
            selectedSubscriptionPeriod = durationInMonths
        )
    }
}