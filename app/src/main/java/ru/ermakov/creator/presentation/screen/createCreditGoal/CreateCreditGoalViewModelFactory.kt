package ru.ermakov.creator.presentation.screen.createCreditGoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.createCreditGoal.CreateCreditGoalUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class CreateCreditGoalViewModelFactory(
    private val createCreditGoalUseCase: CreateCreditGoalUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateCreditGoalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateCreditGoalViewModel(
                createCreditGoalUseCase = createCreditGoalUseCase,
                getCurrentUserIdUseCase = getCurrentUserIdUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw Exception("Unknown ViewModel class")
    }
}