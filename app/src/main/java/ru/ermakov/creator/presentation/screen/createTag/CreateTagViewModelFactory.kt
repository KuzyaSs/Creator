package ru.ermakov.creator.presentation.screen.createTag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.createTag.InsertTagUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class CreateTagViewModelFactory(
    private val insertTagUseCase: InsertTagUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateTagViewModel::class.java)) {
            return CreateTagViewModel(
                insertTagUseCase = insertTagUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
