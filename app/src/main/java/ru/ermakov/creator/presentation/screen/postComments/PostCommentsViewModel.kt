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
import ru.ermakov.creator.domain.model.PostCommentItem
import ru.ermakov.creator.domain.model.PostCommentLikeRequest
import ru.ermakov.creator.domain.model.PostCommentRequest
import ru.ermakov.creator.domain.useCase.postComments.DeletePostCommentByIdUseCase
import ru.ermakov.creator.domain.useCase.postComments.DeletePostCommentLikeUseCase
import ru.ermakov.creator.domain.useCase.postComments.GetPostCommentByCommentAndUserIdsUseCase
import ru.ermakov.creator.domain.useCase.postComments.GetPostCommentPageByPostAndUserIdsUseCase
import ru.ermakov.creator.domain.useCase.postComments.InsertPostCommentLikeUseCase
import ru.ermakov.creator.domain.useCase.postComments.InsertPostCommentUseCase
import ru.ermakov.creator.domain.useCase.postComments.UpdatePostCommentUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

private const val LOAD_NEXT_POST_COMMENT_PAGE_DELAY = 1000L
private const val POST_COMMENT_SENDING_DELAY = 250L
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
                        currentUserId = getCurrentUserIdUseCase(),
                        postCommentItems = getPostCommentPageByPostAndUserIdsUseCase(
                            postId = postId,
                            replyCommentId = UNSELECTED_COMMENT_ID,
                            commentId = DEFAULT_COMMENT_ID
                        ),
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _postCommentsUiState.postValue(
                    _postCommentsUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun refreshPostCommentScreen(postId: Long) {
        _postCommentsUiState.value = _postCommentsUiState.value?.copy(
            isRefreshingShown = true
        )
        setPostCommentsScreen(postId = postId)
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
                    replyCommentId = UNSELECTED_COMMENT_ID,
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

    fun sendPostComment(postId: Long, content: String) {
        _postCommentsUiState.value = _postCommentsUiState.value?.copy(
            isLoadingDialogShown = true,
            isErrorMessageShown = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postCommentRequest = PostCommentRequest(
                    userId = getCurrentUserIdUseCase(),
                    postId = postId,
                    replyCommentId = UNSELECTED_COMMENT_ID,
                    content = content
                )
                val newPostCommentId = insertPostCommentUseCase(
                    postCommentRequest = postCommentRequest
                )
                val newPostCommentItem = getPostCommentByCommentAndUserIdsUseCase(
                    postCommentId = newPostCommentId
                )
                val currentPostCommentItems = _postCommentsUiState
                    .value
                    ?.postCommentItems ?: listOf()
                _postCommentsUiState.postValue(
                    _postCommentsUiState.value?.copy(
                        postCommentItems = (currentPostCommentItems + newPostCommentItem).sortedByDescending { postComment ->
                            postComment.id
                        },
                        isLoadingDialogShown = false,
                        isErrorMessageShown = false,
                    )
                )
                delay(POST_COMMENT_SENDING_DELAY)
                _postCommentsUiState.postValue(_postCommentsUiState.value?.copy(isPostCommentSent = true))
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _postCommentsUiState.postValue(
                    _postCommentsUiState.value?.copy(
                        isLoadingDialogShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun editPostComment(postId: Long, content: String) {
        _postCommentsUiState.value = _postCommentsUiState.value?.copy(
            isLoadingDialogShown = true,
            isErrorMessageShown = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val editedPostCommentItem = _postCommentsUiState.value?.editedPostCommentItem
                val postCommentId = editedPostCommentItem?.id ?: UNSELECTED_COMMENT_ID
                val postCommentRequest = PostCommentRequest(
                    userId = getCurrentUserIdUseCase(),
                    postId = postId,
                    replyCommentId = UNSELECTED_COMMENT_ID,
                    content = content
                )
                updatePostCommentUseCase(
                    postCommentId = postCommentId,
                    postCommentRequest = postCommentRequest
                )
                withContext(Dispatchers.Main) {
                    _postCommentsUiState.value = _postCommentsUiState.value?.copy(
                        isEditCommentMode = false,
                        isPostCommentEdited = true,
                        isLoadingDialogShown = false,
                        isErrorMessageShown = false,
                    )
                }
                updatePostComment(postComment = editedPostCommentItem)
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _postCommentsUiState.postValue(
                    _postCommentsUiState.value?.copy(
                        isLoadingDialogShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun setSelectedPostComment(postComment: PostCommentItem) {
        _postCommentsUiState.value = _postCommentsUiState.value?.copy(
            selectedPostCommentItem = postComment
        )
    }

    fun setEditedPostComment() {
        _postCommentsUiState.value = _postCommentsUiState.value?.copy(
            editedPostCommentItem = _postCommentsUiState.value?.selectedPostCommentItem,
            isEditCommentMode = true
        )
    }

    fun updatePostComment(
        postComment: PostCommentItem? = _postCommentsUiState.value?.selectedPostCommentItem
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedPostCommentItem = getPostCommentByCommentAndUserIdsUseCase(
                    postCommentId = postComment?.id ?: UNSELECTED_COMMENT_ID
                )
                val postCommentItems = _postCommentsUiState
                    .value
                    ?.postCommentItems?.map { postCommentItem ->
                        if (postCommentItem.id == postComment?.id) {
                            updatedPostCommentItem
                        } else {
                            postCommentItem
                        }
                    }
                _postCommentsUiState.postValue(
                    _postCommentsUiState.value?.copy(
                        postCommentItems = postCommentItems
                    )
                )
            } catch (exception: Exception) {
                if (exception is CommentNotFoundException) {
                    val remainingPostCommentItems = _postCommentsUiState
                        .value
                        ?.postCommentItems
                        ?.filter { postCommentItem ->
                            postCommentItem.id != postComment?.id
                        }
                    _postCommentsUiState.postValue(
                        _postCommentsUiState.value?.copy(
                            postCommentItems = remainingPostCommentItems
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
                val selectedPostCommentItem = _postCommentsUiState.value?.selectedPostCommentItem
                deletePostCommentByIdUseCase(
                    postCommentId = selectedPostCommentItem?.id ?: UNSELECTED_COMMENT_ID
                )
                val remainingPostCommentItems = _postCommentsUiState
                    .value
                    ?.postCommentItems
                    ?.filter { postCommentItem ->
                        postCommentItem.id != selectedPostCommentItem?.id
                    }

                _postCommentsUiState.postValue(
                    _postCommentsUiState.value?.copy(
                        postCommentItems = remainingPostCommentItems,
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
                updatePostComment()
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
                updatePostComment()
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

    fun clearEditCommentMode() {
        _postCommentsUiState.value = _postCommentsUiState.value?.copy(isEditCommentMode = false)
    }

    fun clearIsPostCommentSent() {
        _postCommentsUiState.value = _postCommentsUiState.value?.copy(isPostCommentSent = false)
    }

    fun clearIsPostCommentEdited() {
        _postCommentsUiState.value = _postCommentsUiState.value?.copy(isPostCommentEdited = false)
    }

    fun clearErrorMessage() {
        _postCommentsUiState.value = _postCommentsUiState.value?.copy(
            isErrorMessageShown = false,
            errorMessage = ""
        )
    }
}