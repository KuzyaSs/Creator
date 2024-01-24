package ru.ermakov.creator.presentation.screen.donateToCreditGoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.shared.GetBalanceByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.InsertUserTransactionUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class DonateToCreditGoalViewModelFactory(
    private val insertUserTransactionUseCase: InsertUserTransactionUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getBalanceByUserIdUseCase: GetBalanceByUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DonateToCreditGoalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DonateToCreditGoalViewModel(
                insertUserTransactionUseCase = insertUserTransactionUseCase,
                getCurrentUserIdUseCase = getCurrentUserIdUseCase,
                getBalanceByUserIdUseCase = getBalanceByUserIdUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw Exception("Unknown ViewModel class")
    }
}