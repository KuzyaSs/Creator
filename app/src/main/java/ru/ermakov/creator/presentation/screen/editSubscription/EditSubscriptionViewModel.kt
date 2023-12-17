package ru.ermakov.creator.presentation.screen.editSubscription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.SubscriptionRequest
import ru.ermakov.creator.domain.useCase.editSubscription.EditSubscriptionUseCase
import ru.ermakov.creator.domain.useCase.editSubscription.GetSubscriptionByIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class EditSubscriptionViewModel(
    private val getSubscriptionByIdUseCase: GetSubscriptionByIdUseCase,
    private val editSubscriptionUseCase: EditSubscriptionUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) :
    ViewModel() {
    private val _editSubscriptionUiState = MutableLiveData(EditSubscriptionUiState())
    val editSubscriptionUiState: LiveData<EditSubscriptionUiState> = _editSubscriptionUiState

    fun setSubscription(subscriptionId: Long) {
        _editSubscriptionUiState.value = _editSubscriptionUiState.value?.copy(
            isLoading = true,
            isErrorMessageShown = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _editSubscriptionUiState.postValue(
                    _editSubscriptionUiState.value?.copy(
                        subscription = getSubscriptionByIdUseCase(subscriptionId = subscriptionId),
                        isRefreshingShown = false,
                        isLoading = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _editSubscriptionUiState.postValue(
                    _editSubscriptionUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoading = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun refreshSubscription(subscriptionId: Long) {
        _editSubscriptionUiState.value = _editSubscriptionUiState.value?.copy(
            isRefreshingShown = true
        )
        setSubscription(subscriptionId = subscriptionId)
    }

    fun editSubscription(subscriptionId: Long, title: String, description: String, price: Long) {
        _editSubscriptionUiState.postValue(
            _editSubscriptionUiState.value?.copy(
                isProgressBarSaveChangesShown = true,
                isErrorMessageShown = false,
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val subscriptionRequest = SubscriptionRequest(
                    creatorId = getCurrentUserIdUseCase(),
                    title = title,
                    description = description,
                    price = price
                )
                editSubscriptionUseCase(
                    subscriptionId = subscriptionId,
                    subscriptionRequest = subscriptionRequest
                )
                _editSubscriptionUiState.postValue(
                    _editSubscriptionUiState.value?.copy(
                        isSubscriptionEdited = true,
                        isProgressBarSaveChangesShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _editSubscriptionUiState.postValue(
                    _editSubscriptionUiState.value?.copy(
                        isProgressBarSaveChangesShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }
}