package ru.ermakov.creator.presentation.screen.createSubscription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.SubscriptionRequest
import ru.ermakov.creator.domain.useCase.createSubscription.CreateSubscriptionUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class CreateSubscriptionViewModel(
    private val createSubscriptionUseCase: CreateSubscriptionUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) :
    ViewModel() {
    private val _createSubscriptionUiState = MutableLiveData(CreateSubscriptionUiState())
    val createSubscriptionUiState: LiveData<CreateSubscriptionUiState> = _createSubscriptionUiState

    fun createSubscription(title: String, description: String, price: Long) {
        _createSubscriptionUiState.postValue(
            _createSubscriptionUiState.value?.copy(
                isProgressBarCreateShown = true,
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
                createSubscriptionUseCase(subscriptionRequest = subscriptionRequest)
                _createSubscriptionUiState.postValue(
                    _createSubscriptionUiState.value?.copy(
                        isSubscriptionCreated = true,
                        isProgressBarCreateShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _createSubscriptionUiState.postValue(
                    _createSubscriptionUiState.value?.copy(
                        isProgressBarCreateShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }
}