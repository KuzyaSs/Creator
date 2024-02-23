package ru.ermakov.creator.presentation.screen.search.searchPost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ermakov.creator.domain.model.PostItem
import ru.ermakov.creator.domain.useCase.following.DeleteLikeFromPostUseCase
import ru.ermakov.creator.domain.useCase.following.DeletePostByIdUseCase
import ru.ermakov.creator.domain.useCase.following.GetPostByUserAndPostIdsUseCase
import ru.ermakov.creator.domain.useCase.following.InsertLikeToPostUseCase
import ru.ermakov.creator.domain.useCase.search.GetPostPageByUserIdAndSearchQueryUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.screen.following.UNSELECTED_POST_ID
import ru.ermakov.creator.presentation.util.ExceptionHandler

private const val SEARCH_POSTS_DELAY = 1000L
private const val DEFAULT_POST_ID = Long.MAX_VALUE

class SearchPostViewModel(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getPostPageByUserIdAndSearchQueryUseCase: GetPostPageByUserIdAndSearchQueryUseCase,
    private val deletePostByIdUseCase: DeletePostByIdUseCase,
    private val getPostByUserAndPostIdsUseCase: GetPostByUserAndPostIdsUseCase,
    private val insertLikeToPostUseCase: InsertLikeToPostUseCase,
    private val deleteLikeFromPostUseCase: DeleteLikeFromPostUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _searchPostUiState = MutableLiveData(SearchPostUiState())
    val searchPostUiState: LiveData<SearchPostUiState> get() = _searchPostUiState

    private var searchPostsJob: Job? = null
    private var loadNextPostPageJob: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = getCurrentUserIdUseCase()
            _searchPostUiState.postValue(_searchPostUiState.value?.copy(currentUserId = userId))
        }
    }

    fun searchPosts(searchQuery: String) {
        if (searchQuery == _searchPostUiState.value?.lastSearchQuery) {
            return
        }

        searchPostsJob?.cancel()
        loadNextPostPageJob?.cancel()
        if (searchQuery.isBlank()) {
            resetSearchPostUiState()
            return
        }

        _searchPostUiState.value = _searchPostUiState.value?.copy(
            postItems = listOf(),
            lastSearchQuery = searchQuery,
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        searchPostsJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(SEARCH_POSTS_DELAY)
                val postItems: List<PostItem> = getPostPageByUserIdAndSearchQueryUseCase(
                    userId = _searchPostUiState.value?.currentUserId,
                    searchQuery = searchQuery,
                    postId = _searchPostUiState.value?.postItems?.lastOrNull()?.id ?: DEFAULT_POST_ID
                )
                _searchPostUiState.postValue(
                    _searchPostUiState.value?.copy(
                        postItems = postItems,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                if (errorMessage.isNotBlank()) {
                    _searchPostUiState.postValue(
                        _searchPostUiState.value?.copy(
                            postItems = listOf(),
                            isLoadingShown = false,
                            isErrorMessageShown = true,
                            errorMessage = errorMessage
                        )
                    )
                }
            }
        }
    }

    fun loadNextPostPage(searchQuery: String) {
        if (_searchPostUiState.value?.isLoadingShown == true) {
            return
        }

        loadNextPostPageJob?.cancel()
        _searchPostUiState.value = _searchPostUiState.value?.copy(
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        loadNextPostPageJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(SEARCH_POSTS_DELAY)
                val currentPosts = _searchPostUiState.value?.postItems ?: listOf()
                val nextPosts = getPostPageByUserIdAndSearchQueryUseCase(
                    userId = _searchPostUiState.value?.currentUserId,
                    searchQuery = searchQuery,
                    postId = _searchPostUiState.value?.postItems?.lastOrNull()?.id ?: DEFAULT_POST_ID
                )
                _searchPostUiState.postValue(
                    _searchPostUiState.value?.copy(
                        postItems = currentPosts + nextPosts,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                if (errorMessage.isNotBlank()) {
                    _searchPostUiState.postValue(
                        _searchPostUiState.value?.copy(
                            isLoadingShown = false,
                            isErrorMessageShown = true,
                            errorMessage = errorMessage
                        )
                    )
                }
            }
        }
    }

    private fun resetSearchPostUiState() {
        _searchPostUiState.value = _searchPostUiState.value?.copy(
            postItems = listOf(),
            lastSearchQuery = "",
            isLoadingShown = false,
            isErrorMessageShown = false
        )
    }

    fun insertLikeToPost(postId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                insertLikeToPostUseCase(
                    userId = _searchPostUiState.value?.currentUserId,
                    postId = postId
                )
                withContext(Dispatchers.Main) {
                    setSelectedPostId(postId = postId)
                }
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _searchPostUiState.postValue(
                    _searchPostUiState.value?.copy(
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun deleteLikeFromPost(postId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteLikeFromPostUseCase(
                    userId = _searchPostUiState.value?.currentUserId,
                    postId = postId
                )
                withContext(Dispatchers.Main) {
                    setSelectedPostId(postId = postId)
                }
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _searchPostUiState.postValue(
                    _searchPostUiState.value?.copy(
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun setSelectedPostId(postId: Long) {
        _searchPostUiState.value = _searchPostUiState.value?.copy(selectedPostId = postId)
        updateSelectedPostId()
    }

    fun updateSelectedPostId() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = _searchPostUiState.value?.currentUserId
                val selectedPostId = _searchPostUiState.value?.selectedPostId ?: UNSELECTED_POST_ID
                val updatedPostItem = getPostByUserAndPostIdsUseCase(
                    userId = userId,
                    postId = selectedPostId
                )

                val postItems = _searchPostUiState.value?.postItems?.map { postItem ->
                    if (postItem.id == selectedPostId) {
                        updatedPostItem
                    } else {
                        postItem
                    }
                }
                _searchPostUiState.postValue(_searchPostUiState.value?.copy(postItems = postItems))
            } catch (_: Exception) {
            }
        }
    }

    fun deleteSelectedPost() {
        _searchPostUiState.value = _searchPostUiState.value?.copy(isErrorMessageShown = false)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val selectedPostId = _searchPostUiState.value?.selectedPostId ?: UNSELECTED_POST_ID
                deletePostByIdUseCase(postId = selectedPostId)

                val remainingPostItems = _searchPostUiState.value?.postItems?.filter { postItem ->
                    postItem.id != selectedPostId
                }

                _searchPostUiState.postValue(
                    _searchPostUiState.value?.copy(
                        postItems = remainingPostItems,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _searchPostUiState.postValue(
                    _searchPostUiState.value?.copy(
                        isLoadingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun clearErrorMessage() {
        _searchPostUiState.value = _searchPostUiState.value?.copy(
            isErrorMessageShown = false,
            errorMessage = ""
        )
    }
}