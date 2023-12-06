package ru.ermakov.creator.presentation.screen.search.searchCreator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import ru.ermakov.creator.domain.useCase.search.SearchCreatorsUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class SearchCreatorViewModelFactory(
    private val searchCreatorsUseCase: SearchCreatorsUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(SearchCreatorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchCreatorViewModel(
                searchCreatorsUseCase = searchCreatorsUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}