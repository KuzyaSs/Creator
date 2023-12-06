package ru.ermakov.creator.presentation.screen.search.searchPost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import ru.ermakov.creator.presentation.util.ExceptionHandler

class SearchPostViewModelFactory(
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(SearchPostViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchPostViewModel(exceptionHandler = exceptionHandler) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}