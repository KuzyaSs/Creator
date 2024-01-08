package ru.ermakov.creator.presentation.screen.follows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.follows.SearchFollowPageByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class FollowsViewModelFactory(
    private val searchFollowPageByUserIdUseCase: SearchFollowPageByUserIdUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FollowsViewModel(
                searchFollowPageByUserIdUseCase = searchFollowPageByUserIdUseCase,
                getCurrentUserIdUseCase = getCurrentUserIdUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}