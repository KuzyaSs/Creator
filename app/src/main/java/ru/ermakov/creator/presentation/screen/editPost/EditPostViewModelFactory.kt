package ru.ermakov.creator.presentation.screen.editPost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.blog.GetTagsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.editPost.EditPostUseCase
import ru.ermakov.creator.domain.useCase.following.GetPostByUserAndPostIdsUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.GetSubscriptionsByCreatorIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class EditPostViewModelFactory(
    private val getPostByUserAndPostIdsUseCase: GetPostByUserAndPostIdsUseCase,
    private val getTagsByCreatorIdUseCase: GetTagsByCreatorIdUseCase,
    private val getSubscriptionsByCreatorIdUseCase: GetSubscriptionsByCreatorIdUseCase,
    private val editPostUseCase: EditPostUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditPostViewModel::class.java)) {
            return EditPostViewModel(
                getPostByUserAndPostIdsUseCase = getPostByUserAndPostIdsUseCase,
                getTagsByCreatorIdUseCase = getTagsByCreatorIdUseCase,
                getSubscriptionsByCreatorIdUseCase = getSubscriptionsByCreatorIdUseCase,
                editPostUseCase = editPostUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw Exception("Unknown ViewModel class")
    }
}
