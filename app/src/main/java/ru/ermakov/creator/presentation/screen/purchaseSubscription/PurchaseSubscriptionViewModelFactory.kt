package ru.ermakov.creator.presentation.screen.purchaseSubscription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.editSubscription.GetSubscriptionByIdUseCase
import ru.ermakov.creator.domain.useCase.purchaseSubscription.PurchaseSubscriptionUseCase
import ru.ermakov.creator.domain.useCase.shared.GetBalanceByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class PurchaseSubscriptionViewModelFactory(
    private val getSubscriptionByIdUseCase: GetSubscriptionByIdUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getBalanceByUserIdUseCase: GetBalanceByUserIdUseCase,
    private val purchaseSubscriptionUseCase: PurchaseSubscriptionUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PurchaseSubscriptionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PurchaseSubscriptionViewModel(
                getSubscriptionByIdUseCase = getSubscriptionByIdUseCase,
                getCurrentUserIdUseCase = getCurrentUserIdUseCase,
                getBalanceByUserIdUseCase = getBalanceByUserIdUseCase,
                purchaseSubscriptionUseCase = purchaseSubscriptionUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw Exception("Unknown ViewModel class")
    }
}