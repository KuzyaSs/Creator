package ru.ermakov.creator.presentation.screen.editSubscription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.editSubscription.EditSubscriptionUseCase
import ru.ermakov.creator.domain.useCase.editSubscription.GetSubscriptionByIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class EditSubscriptionViewModelFactory(
    private val getSubscriptionByIdUseCase: GetSubscriptionByIdUseCase,
    private val editSubscriptionUseCase: EditSubscriptionUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditSubscriptionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditSubscriptionViewModel(
                getSubscriptionByIdUseCase = getSubscriptionByIdUseCase,
                editSubscriptionUseCase = editSubscriptionUseCase,
                getCurrentUserIdUseCase = getCurrentUserIdUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw Exception("Unknown ViewModel class")
    }
}