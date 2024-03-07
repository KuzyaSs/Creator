package ru.ermakov.creator.presentation.screen.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.data.exception.PostNotFoundException
import ru.ermakov.creator.domain.useCase.following.DeleteLikeFromPostUseCase
import ru.ermakov.creator.domain.useCase.following.DeletePostByIdUseCase
import ru.ermakov.creator.domain.useCase.following.GetPostByUserAndPostIdsUseCase
import ru.ermakov.creator.domain.useCase.following.InsertLikeToPostUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

const val INVALID_POST_ID = -1L

class PostViewModel(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val deletePostByIdUseCase: DeletePostByIdUseCase,
    private val getPostByUserAndPostIdsUseCase: GetPostByUserAndPostIdsUseCase,
    private val insertLikeToPostUseCase: InsertLikeToPostUseCase,
    private val deleteLikeFromPostUseCase: DeleteLikeFromPostUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _postUiState = MutableLiveData(PostUiState())
    val postUiState: LiveData<PostUiState> = _postUiState

    fun setPostScreen(postId: Long) {
        _postUiState.postValue(
            _postUiState.value?.copy(
                isLoadingShown = true,
                isErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUserId = getCurrentUserIdUseCase()
                _postUiState.postValue(
                    _postUiState.value?.copy(
                        currentUserId = currentUserId,
                        postItem = getPostByUserAndPostIdsUseCase(
                            userId = currentUserId,
                            postId = postId
                        ),
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _postUiState.postValue(
                    _postUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun refreshPostScreen(postId: Long) {
        _postUiState.value = _postUiState.value?.copy(isRefreshingShown = true)
        setPostScreen(postId = postId)
    }

    fun updatePost(postId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = getCurrentUserIdUseCase()
                val updatedPostItem = getPostByUserAndPostIdsUseCase(
                    userId = userId,
                    postId = postId
                )
                _postUiState.postValue(_postUiState.value?.copy(postItem = updatedPostItem))
            } catch (exception: Exception) {
                if (exception is PostNotFoundException) {
                    _postUiState.postValue(_postUiState.value?.copy(isPostDeleted = true))
                }
            }
        }
    }

    fun deletePost(postId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deletePostByIdUseCase(postId = postId)
                _postUiState.postValue(
                    _postUiState.value?.copy(
                        isPostDeleted = true,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _postUiState.postValue(
                    _postUiState.value?.copy(
                        isLoadingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun insertLikeToPost(postId: Long) {
        val currentPostItem = _postUiState.value?.postItem
        _postUiState.value = _postUiState.value?.copy(
            postItem = currentPostItem?.copy(
                likeCount = currentPostItem.likeCount.inc(),
                isLiked = true
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                insertLikeToPostUseCase(
                    userId = _postUiState.value?.currentUserId,
                    postId = postId
                )
                updatePost(postId = postId)
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _postUiState.postValue(
                    _postUiState.value?.copy(
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun deleteLikeFromPost(postId: Long) {
        val currentPostItem = _postUiState.value?.postItem
        _postUiState.value = _postUiState.value?.copy(
            postItem = currentPostItem?.copy(
                likeCount = currentPostItem.likeCount.dec(),
                isLiked = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteLikeFromPostUseCase(
                    userId = _postUiState.value?.currentUserId,
                    postId = postId
                )
                updatePost(postId = postId)
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _postUiState.postValue(
                    _postUiState.value?.copy(
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun clearErrorMessage() {
        _postUiState.value = _postUiState.value?.copy(
            isErrorMessageShown = false,
            errorMessage = ""
        )
    }
}