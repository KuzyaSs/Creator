package ru.ermakov.creator.presentation.screen.subscriptions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ermakov.creator.domain.useCase.createSubscription.DeleteSubscriptionByIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.GetSubscriptionsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.GetUserSubscriptionsByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.UnsubscribeUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class SubscriptionsViewModel(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getUserSubscriptionsByUserAndCreatorIdsUseCase: GetUserSubscriptionsByUserAndCreatorIdsUseCase,
    private val getSubscriptionsByCreatorIdUseCase: GetSubscriptionsByCreatorIdUseCase,
    private val unsubscribeUseCase: UnsubscribeUseCase,
    private val deleteSubscriptionByIdUseCase: DeleteSubscriptionByIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _subscriptionsUiState = MutableLiveData(SubscriptionsUiState())
    val subscriptionsUiState: LiveData<SubscriptionsUiState> = _subscriptionsUiState

    fun setSubscriptions(creatorId: String) {
        _subscriptionsUiState.postValue(
            _subscriptionsUiState.value?.copy(
                isLoading = true,
                isErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUserId = getCurrentUserIdUseCase()
                _subscriptionsUiState.postValue(
                    _subscriptionsUiState.value?.copy(
                        currentUserId = currentUserId,
                        userSubscriptions = getUserSubscriptionsByUserAndCreatorIdsUseCase(
                            userId = currentUserId,
                            creatorId = creatorId
                        ),
                        creatorId = creatorId,
                        subscriptions = getSubscriptionsByCreatorIdUseCase(creatorId = creatorId),
                        isRefreshingShown = false,
                        isLoading = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _subscriptionsUiState.postValue(
                    _subscriptionsUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoading = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun refreshSubscriptions(creatorId: String) {
        _subscriptionsUiState.value = _subscriptionsUiState.value?.copy(
            isRefreshingShown = true
        )
        setSubscriptions(creatorId = creatorId)
    }

    fun deleteSelectedSubscription() {
        _subscriptionsUiState.postValue(
            _subscriptionsUiState.value?.copy(
                isRefreshingShown = true,
                isErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteSubscriptionByIdUseCase(
                    subscriptionId = _subscriptionsUiState.value?.selectedSubscriptionId
                        ?: UNSELECTED_SUBSCRIPTION_ID
                )
                withContext(Dispatchers.Main) {
                    refreshSubscriptions(creatorId = _subscriptionsUiState.value?.creatorId ?: "")
                }
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _subscriptionsUiState.postValue(
                    _subscriptionsUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoading = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun unsubscribeFromSelectedSubscription() {
        _subscriptionsUiState.postValue(
            _subscriptionsUiState.value?.copy(
                isRefreshingShown = true,
                isErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                unsubscribeUseCase(
                    userId = getCurrentUserIdUseCase(),
                    userSubscriptionId = _subscriptionsUiState.value?.selectedUserSubscriptionId
                        ?: UNSELECTED_USER_SUBSCRIPTION_ID
                )
                withContext(Dispatchers.Main) {
                    refreshSubscriptions(creatorId = _subscriptionsUiState.value?.creatorId ?: "")
                }
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _subscriptionsUiState.postValue(
                    _subscriptionsUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoading = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun setSelectedSubscriptionId(subscriptionId: Long) {
        _subscriptionsUiState.value = _subscriptionsUiState.value?.copy(
            selectedSubscriptionId = subscriptionId
        )
    }

    fun setSelectedUserSubscriptionId(userSubscriptionId: Long) {
        _subscriptionsUiState.value = _subscriptionsUiState.value?.copy(
            selectedUserSubscriptionId = userSubscriptionId
        )
    }

    fun clearErrorMessage() {
        _subscriptionsUiState.value = _subscriptionsUiState.value?.copy(
            isErrorMessageShown = false,
            errorMessage = ""
        )
    }
}