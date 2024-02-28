package ru.ermakov.creator.presentation.screen.editTag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.editTag.EditTagUseCase
import ru.ermakov.creator.domain.useCase.editTag.GetTagByIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class EditTagViewModelFactory(
    private val getTagByIdUseCase: GetTagByIdUseCase,
    private val editTagUseCase: EditTagUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditTagViewModel::class.java)) {
            return EditTagViewModel(
                getTagByIdUseCase = getTagByIdUseCase,
                editTagUseCase = editTagUseCase,
                getCurrentUserIdUseCase = getCurrentUserIdUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}