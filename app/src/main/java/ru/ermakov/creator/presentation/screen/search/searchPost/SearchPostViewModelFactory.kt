package ru.ermakov.creator.presentation.screen.search.searchPost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import ru.ermakov.creator.domain.useCase.following.DeleteLikeFromPostUseCase
import ru.ermakov.creator.domain.useCase.following.DeletePostByIdUseCase
import ru.ermakov.creator.domain.useCase.following.GetPostByUserAndPostIdsUseCase
import ru.ermakov.creator.domain.useCase.following.InsertLikeToPostUseCase
import ru.ermakov.creator.domain.useCase.search.GetPostPageByUserIdAndSearchQueryUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class SearchPostViewModelFactory(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getPostPageByUserIdAndSearchQueryUseCase: GetPostPageByUserIdAndSearchQueryUseCase,
    private val deletePostByIdUseCase: DeletePostByIdUseCase,
    private val getPostByUserAndPostIdsUseCase: GetPostByUserAndPostIdsUseCase,
    private val insertLikeToPostUseCase: InsertLikeToPostUseCase,
    private val deleteLikeFromPostUseCase: DeleteLikeFromPostUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(SearchPostViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchPostViewModel(
                getCurrentUserIdUseCase = getCurrentUserIdUseCase,
                getPostPageByUserIdAndSearchQueryUseCase = getPostPageByUserIdAndSearchQueryUseCase,
                deletePostByIdUseCase = deletePostByIdUseCase,
                getPostByUserAndPostIdsUseCase = getPostByUserAndPostIdsUseCase,
                insertLikeToPostUseCase = insertLikeToPostUseCase,
                deleteLikeFromPostUseCase = deleteLikeFromPostUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}