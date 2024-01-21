package ru.ermakov.creator.presentation.screen.editCreditGoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.editCreditGoal.EditCreditGoalUseCase
import ru.ermakov.creator.domain.useCase.editCreditGoal.GetCreditGoalByIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class EditCreditGoalViewModelFactory(
    private val getCreditGoalByIdUseCase: GetCreditGoalByIdUseCase,
    private val editCreditGoalUseCase: EditCreditGoalUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditCreditGoalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditCreditGoalViewModel(
                getCreditGoalByIdUseCase = getCreditGoalByIdUseCase,
                editCreditGoalUseCase = editCreditGoalUseCase,
                getCurrentUserIdUseCase = getCurrentUserIdUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw Exception("Unknown ViewModel class")
    }
}