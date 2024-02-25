package ru.ermakov.creator.presentation.screen.createPost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.blog.GetTagsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.createPost.PublishPostUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.GetSubscriptionsByCreatorIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class CreatePostViewModelFactory(
    private val getTagsByCreatorIdUseCase: GetTagsByCreatorIdUseCase,
    private val getSubscriptionsByCreatorIdUseCase: GetSubscriptionsByCreatorIdUseCase,
    private val publishPostUseCase: PublishPostUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreatePostViewModel::class.java)) {
            return CreatePostViewModel(
                getTagsByCreatorIdUseCase = getTagsByCreatorIdUseCase,
                getSubscriptionsByCreatorIdUseCase = getSubscriptionsByCreatorIdUseCase,
                publishPostUseCase = publishPostUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw Exception("Unknown ViewModel class")
    }
}
