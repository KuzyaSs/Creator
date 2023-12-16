package ru.ermakov.creator.presentation.screen.createSubscription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.createSubscription.CreateSubscriptionUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class CreateSubscriptionViewModelFactory(
    private val createSubscriptionUseCase: CreateSubscriptionUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateSubscriptionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateSubscriptionViewModel(
                createSubscriptionUseCase = createSubscriptionUseCase,
                getCurrentUserIdUseCase = getCurrentUserIdUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw Exception("Unknown ViewModel class")
    }
}