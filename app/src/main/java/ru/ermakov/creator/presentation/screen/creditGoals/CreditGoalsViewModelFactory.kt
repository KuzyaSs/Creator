package ru.ermakov.creator.presentation.screen.creditGoals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.creditGoals.CloseCreditGoalUseCase
import ru.ermakov.creator.domain.useCase.creditGoals.GetCreditGoalsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class CreditGoalsViewModelFactory(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getCreditGoalsByCreatorIdUseCase: GetCreditGoalsByCreatorIdUseCase,
    private val closeCreditGoalUseCase: CloseCreditGoalUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreditGoalsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreditGoalsViewModel(
                getCurrentUserIdUseCase = getCurrentUserIdUseCase,
                getCreditGoalsByCreatorIdUseCase = getCreditGoalsByCreatorIdUseCase,
                closeCreditGoalUseCase = closeCreditGoalUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}