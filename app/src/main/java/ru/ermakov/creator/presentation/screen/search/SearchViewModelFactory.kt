package ru.ermakov.creator.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import ru.ermakov.creator.presentation.exception.ExceptionHandler

class SearchViewModelFactory(
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(exceptionHandler = exceptionHandler) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}