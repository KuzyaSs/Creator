package ru.ermakov.creator.presentation.screen.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.following.DeleteLikeFromPostUseCase
import ru.ermakov.creator.domain.useCase.following.DeletePostByIdUseCase
import ru.ermakov.creator.domain.useCase.following.GetPostByUserAndPostIdsUseCase
import ru.ermakov.creator.domain.useCase.following.InsertLikeToPostUseCase
import ru.ermakov.creator.domain.useCase.post.GetCommentPageByPostAndReplyCommentIdsUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class PostViewModelFactory(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getCommentPageByPostAndReplyCommentIdsUseCase: GetCommentPageByPostAndReplyCommentIdsUseCase,
    private val deletePostByIdUseCase: DeletePostByIdUseCase,
    private val getPostByUserAndPostIdsUseCase: GetPostByUserAndPostIdsUseCase,
    private val insertLikeToPostUseCase: InsertLikeToPostUseCase,
    private val deleteLikeFromPostUseCase: DeleteLikeFromPostUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(
                getCurrentUserIdUseCase = getCurrentUserIdUseCase,
                getCommentPageByPostAndReplyCommentIdsUseCase = getCommentPageByPostAndReplyCommentIdsUseCase,
                deletePostByIdUseCase = deletePostByIdUseCase,
                getPostByUserAndPostIdsUseCase = getPostByUserAndPostIdsUseCase,
                insertLikeToPostUseCase = insertLikeToPostUseCase,
                deleteLikeFromPostUseCase = deleteLikeFromPostUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw Exception("Unknown ViewModel class")
    }
}
