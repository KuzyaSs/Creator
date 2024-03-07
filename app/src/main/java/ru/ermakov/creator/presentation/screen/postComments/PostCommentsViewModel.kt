package ru.ermakov.creator.presentation.screen.postComments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ermakov.creator.data.exception.CommentNotFoundException
import ru.ermakov.creator.data.mapper.INVALID_COMMENT_ID
import ru.ermakov.creator.domain.model.PostCommentLikeRequest
import ru.ermakov.creator.domain.useCase.postComments.DeletePostCommentByIdUseCase
import ru.ermakov.creator.domain.useCase.postComments.DeletePostCommentLikeUseCase
import ru.ermakov.creator.domain.useCase.postComments.GetPostCommentPageByPostAndUserIdsUseCase
import ru.ermakov.creator.domain.useCase.postComments.GetPostCommentByCommentAndUserIdsUseCase
import ru.ermakov.creator.domain.useCase.postComments.InsertPostCommentLikeUseCase
import ru.ermakov.creator.domain.useCase.postComments.InsertPostCommentUseCase
import ru.ermakov.creator.domain.useCase.postComments.UpdatePostCommentUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

private const val LOAD_NEXT_POST_COMMENT_PAGE_DELAY = 1000L
private const val DEFAULT_COMMENT_ID = Long.MAX_VALUE

class PostCommentsViewModel(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getPostCommentPageByPostAndUserIdsUseCase: GetPostCommentPageByPostAndUserIdsUseCase,
    private val getPostCommentByCommentAndUserIdsUseCase: GetPostCommentByCommentAndUserIdsUseCase,
    private val insertPostCommentUseCase: InsertPostCommentUseCase,
    private val updatePostCommentUseCase: UpdatePostCommentUseCase,
    private val deletePostCommentByIdUseCase: DeletePostCommentByIdUseCase,
    private val insertPostCommentLikeUseCase: InsertPostCommentLikeUseCase,
    private val deletePostCommentLikeUseCase: DeletePostCommentLikeUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _postCommentsUiState = MutableLiveData(PostCommentsUiState())
    val postCommentsUiState: LiveData<PostCommentsUiState> = _postCommentsUiState

    private var loadNextPostCommentPageJob: Job? = null

    fun setPostCommentsScreen(postId: Long) {
        _postCommentsUiState.postValue(
            _postCommentsUiState.value?.copy(
                isLoadingShown = true,
                isErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _postCommentsUiState.postValue(
                    _postCommentsUiState.value?.copy(
                        postCommentItems = getPostCommentPageByPostAndUserIdsUseCase(
                            postId = postId,
                            replyCommentId = INVALID_COMMENT_ID,
                            commentId = DEFAULT_COMMENT_ID
                        ),
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _postCommentsUiState.postValue(
                    _postCommentsUiState.value?.copy(
                        isLoadingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun setEditMessageMode(isEditMessageMode: Boolean) {
        _postCommentsUiState.value = _postCommentsUiState.value?.copy(
            isEditCommentMode = isEditMessageMode
        )
    }

    fun loadNextPostCommentPage(postId: Long) {
        if (_postCommentsUiState.value?.isLoadingShown == true) {
            return
        }

        loadNextPostCommentPageJob?.cancel()
        _postCommentsUiState.value = _postCommentsUiState.value?.copy(
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        loadNextPostCommentPageJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(LOAD_NEXT_POST_COMMENT_PAGE_DELAY)
                val currentCommentItems = _postCommentsUiState.value?.postCommentItems ?: listOf()
                val nextCommentItems = getPostCommentPageByPostAndUserIdsUseCase(
                    postId = postId,
                    replyCommentId = INVALID_COMMENT_ID,
                    commentId = _postCommentsUiState.value?.postCommentItems?.lastOrNull()?.id
                        ?: DEFAULT_COMMENT_ID
                )
                _postCommentsUiState.postValue(
                    _postCommentsUiState.value?.copy(
                        postCommentItems = currentCommentItems + nextCommentItems,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                if (errorMessage.isNotBlank()) {
                    _postCommentsUiState.postValue(
                        _postCommentsUiState.value?.copy(
                            isLoadingShown = false,
                            isErrorMessageShown = true,
                            errorMessage = errorMessage
                        )
                    )
                }
            }
        }
    }

    fun insertPostComment(postId: Long, content: String) {

    }

    fun editPostComment(postId: Long, content: String) {

    }

    fun setSelectedPostCommentId(postCommentId: Long) {
        _postCommentsUiState.value = _postCommentsUiState.value?.copy(
            selectedPostCommentId = postCommentId
        )
    }

    fun updateSelectedPostComment() {
        val selectedPostCommentId = _postCommentsUiState.value?.selectedPostCommentId
            ?: UNSELECTED_COMMENT_ID
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedCommentItem = getPostCommentByCommentAndUserIdsUseCase(
                    postCommentId = selectedPostCommentId
                )
                val commentItems =
                    _postCommentsUiState.value?.postCommentItems?.map { commentItem ->
                        if (commentItem.id == selectedPostCommentId) {
                            updatedCommentItem
                        } else {
                            commentItem
                        }
                    }
                _postCommentsUiState.postValue(
                    _postCommentsUiState.value?.copy(
                        postCommentItems = commentItems
                    )
                )
            } catch (exception: Exception) {
                if (exception is CommentNotFoundException) {
                    val remainingCommentItems = _postCommentsUiState
                        .value
                        ?.postCommentItems
                        ?.filter { commentItem ->
                            commentItem.id != selectedPostCommentId
                        }
                    _postCommentsUiState.postValue(
                        _postCommentsUiState.value?.copy(
                            postCommentItems = remainingCommentItems
                        )
                    )
                }
            }
        }
    }

    fun deleteSelectedPostComment() {
        _postCommentsUiState.value = _postCommentsUiState.value?.copy(isErrorMessageShown = false)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val selectedPostCommentId = _postCommentsUiState.value?.selectedPostCommentId
                    ?: UNSELECTED_COMMENT_ID
                deletePostCommentByIdUseCase(postCommentId = selectedPostCommentId)

                val remainingCommentItems = _postCommentsUiState
                    .value
                    ?.postCommentItems
                    ?.filter { commentItem ->
                        commentItem.id != selectedPostCommentId
                    }

                _postCommentsUiState.postValue(
                    _postCommentsUiState.value?.copy(
                        postCommentItems = remainingCommentItems,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _postCommentsUiState.postValue(
                    _postCommentsUiState.value?.copy(
                        isLoadingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun insertLikeToPostComment(postCommentId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postCommentLikeRequest = PostCommentLikeRequest(
                    postCommentId = postCommentId,
                    userId = getCurrentUserIdUseCase()
                )
                insertPostCommentLikeUseCase(postCommentLikeRequest = postCommentLikeRequest)
                updateSelectedPostComment()
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _postCommentsUiState.postValue(
                    _postCommentsUiState.value?.copy(
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun deleteLikeFromPostComment(postCommentId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deletePostCommentLikeUseCase(
                    postCommentId = postCommentId,
                    userId = getCurrentUserIdUseCase()
                )
                updateSelectedPostComment()
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _postCommentsUiState.postValue(
                    _postCommentsUiState.value?.copy(
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun clearErrorMessage() {
        _postCommentsUiState.value = _postCommentsUiState.value?.copy(
            isErrorMessageShown = false,
            errorMessage = ""
        )
    }
}