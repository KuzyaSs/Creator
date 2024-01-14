package ru.ermakov.creator.presentation.screen.balance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.balance.SearchUserTransactionPageByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetBalanceByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class BalanceViewModelFactory(
    private val searchUserTransactionPageByUserIdUseCase: SearchUserTransactionPageByUserIdUseCase,
    private val getBalanceByUserIdUseCase: GetBalanceByUserIdUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BalanceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BalanceViewModel(
                searchUserTransactionPageByUserIdUseCase = searchUserTransactionPageByUserIdUseCase,
                getBalanceByUserIdUseCase = getBalanceByUserIdUseCase,
                getCurrentUserIdUseCase = getCurrentUserIdUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}