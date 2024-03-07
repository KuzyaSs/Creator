package ru.ermakov.creator.presentation.screen.postComments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.postComments.DeletePostCommentByIdUseCase
import ru.ermakov.creator.domain.useCase.postComments.GetPostCommentByCommentAndUserIdsUseCase
import ru.ermakov.creator.domain.useCase.postComments.GetPostCommentPageByPostAndUserIdsUseCase
import ru.ermakov.creator.domain.useCase.postComments.InsertPostCommentUseCase
import ru.ermakov.creator.domain.useCase.postComments.UpdatePostCommentUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class PostCommentsViewModelFactory(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getPostCommentPageByPostAndUserIdsUseCase: GetPostCommentPageByPostAndUserIdsUseCase,
    private val getPostCommentByCommentAndUserIdsUseCase: GetPostCommentByCommentAndUserIdsUseCase,
    private val insertPostCommentUseCase: InsertPostCommentUseCase,
    private val updatePostCommentUseCase: UpdatePostCommentUseCase,
    private val deletePostCommentByIdUseCase: DeletePostCommentByIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostCommentsViewModel::class.java)) {
            return PostCommentsViewModel(
                getCurrentUserIdUseCase = getCurrentUserIdUseCase,
                getPostCommentPageByPostAndUserIdsUseCase = getPostCommentPageByPostAndUserIdsUseCase,
                getPostCommentByCommentAndUserIdsUseCase = getPostCommentByCommentAndUserIdsUseCase,
                insertPostCommentUseCase = insertPostCommentUseCase,
                updatePostCommentUseCase = updatePostCommentUseCase,
                deletePostCommentByIdUseCase = deletePostCommentByIdUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw Exception("Unknown ViewModel class")
    }
}