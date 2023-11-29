package ru.ermakov.creator.presentation.screen.blog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.blog.IsFollowerUseCase
import ru.ermakov.creator.domain.useCase.blog.IsSubscriberUseCase
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class BlogViewModelFactory(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val isFollowerUseCase: IsFollowerUseCase,
    private val isSubscriberUseCase: IsSubscriberUseCase,
    private val exceptionHandler: ExceptionHandler,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BlogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BlogViewModel(
                getCurrentUserIdUseCase = getCurrentUserIdUseCase,
                isFollowerUseCase = isFollowerUseCase,
                isSubscriberUseCase = isSubscriberUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw Exception("Unknown ViewModel class")
    }
}