package ru.ermakov.creator.presentation.screen.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ermakov.creator.data.exception.PostNotFoundException
import ru.ermakov.creator.domain.model.Category
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoryInListUseCase
import ru.ermakov.creator.domain.useCase.discover.GetFilteredPostPageByUserIdUseCase
import ru.ermakov.creator.domain.useCase.following.DeleteLikeFromPostUseCase
import ru.ermakov.creator.domain.useCase.following.DeletePostByIdUseCase
import ru.ermakov.creator.domain.useCase.following.GetAllCategoriesUseCase
import ru.ermakov.creator.domain.useCase.following.GetPostByUserAndPostIdsUseCase
import ru.ermakov.creator.domain.useCase.following.InsertLikeToPostUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserUseCase
import ru.ermakov.creator.presentation.screen.following.DefaultFeedFilter
import ru.ermakov.creator.presentation.screen.following.UNSELECTED_POST_ID
import ru.ermakov.creator.presentation.util.ExceptionHandler

private const val LOAD_NEXT_POST_PAGE_DELAY = 1000L
private const val DEFAULT_POST_ID = Long.MAX_VALUE

class DiscoverViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getFilteredPostPageByUserIdUseCase: GetFilteredPostPageByUserIdUseCase,
    private val updateCategoryInListUseCase: UpdateCategoryInListUseCase,
    private val deletePostByIdUseCase: DeletePostByIdUseCase,
    private val getPostByUserAndPostIdsUseCase: GetPostByUserAndPostIdsUseCase,
    private val insertLikeToPostUseCase: InsertLikeToPostUseCase,
    private val deleteLikeFromPostUseCase: DeleteLikeFromPostUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _discoverUiState = MutableLiveData(DiscoverUiState())
    val discoverUiState: LiveData<DiscoverUiState> get() = _discoverUiState

    private var loadNextPostPageJob: Job? = null
    private var setDiscoverScreenJob: Job? = null

    init {
        setDiscoverScreen()
    }

    fun refreshDiscoverScreen() {
        _discoverUiState.value = _discoverUiState.value?.copy(isRefreshingShown = true)
        setDiscoverScreen()
    }

    fun resetFeedFilter() {
        _discoverUiState.value = _discoverUiState.value?.copy(
            feedFilter = DefaultFeedFilter.defaultFeedFilter,
            categories = _discoverUiState.value?.categories?.map { it.copy(isSelected = false) }
        )
    }

    fun changePostTypeFilter(postType: String) {
        _discoverUiState.value = _discoverUiState.value?.copy(
            feedFilter = _discoverUiState.value?.feedFilter?.copy(
                postType = postType
            ) ?: DefaultFeedFilter.defaultFeedFilter
        )
    }

    fun changeCategoryFilter(changedCategory: Category) {
        val updatedCategories = updateCategoryInListUseCase(
            categories = _discoverUiState.value?.categories,
            changedCategory = changedCategory
        )

        val filteredCategoryIds = _discoverUiState
            .value?.feedFilter
            ?.categoryIds
            ?.toMutableList() ?: mutableListOf()

        if (changedCategory.isSelected && !filteredCategoryIds.contains(changedCategory.id)) {
            filteredCategoryIds.add(changedCategory.id)
        } else {
            filteredCategoryIds.remove(changedCategory.id)
        }

        _discoverUiState.value = _discoverUiState.value?.copy(
            feedFilter = _discoverUiState.value?.feedFilter?.copy(
                categoryIds = filteredCategoryIds
            ) ?: DefaultFeedFilter.defaultFeedFilter,
            categories = updatedCategories
        )
    }

    fun setSelectedPostId(postId: Long) {
        _discoverUiState.value = _discoverUiState.value?.copy(selectedPostId = postId)
        updateSelectedPostId()
    }

    fun updateSelectedPostId() {
        val userId = _discoverUiState.value?.currentUser?.id
        val selectedPostId = _discoverUiState.value?.selectedPostId ?: UNSELECTED_POST_ID
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedPostItem = getPostByUserAndPostIdsUseCase(
                    userId = userId,
                    postId = selectedPostId
                )

                val postItems = _discoverUiState.value?.postItems?.map { postItem ->
                    if (postItem.id == selectedPostId) {
                        updatedPostItem
                    } else {
                        postItem
                    }
                }
                _discoverUiState.postValue(_discoverUiState.value?.copy(postItems = postItems))
            } catch (exception: Exception) {
                if (exception is PostNotFoundException) {
                    val remainingPostItems = _discoverUiState.value?.postItems?.filter { postItem ->
                        postItem.id != selectedPostId
                    }
                    _discoverUiState.postValue(_discoverUiState.value?.copy(postItems = remainingPostItems))
                }
            }
        }
    }

    fun clearErrorMessage() {
        _discoverUiState.value = _discoverUiState.value?.copy(
            isErrorMessageShown = false,
            errorMessage = ""
        )
    }

    fun insertLikeToPost(postId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                insertLikeToPostUseCase(
                    userId = _discoverUiState.value?.currentUser?.id,
                    postId = postId
                )
                withContext(Dispatchers.Main) {
                    setSelectedPostId(postId = postId)
                }
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _discoverUiState.postValue(
                    _discoverUiState.value?.copy(
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
                    userId = _discoverUiState.value?.currentUser?.id,
                    postId = postId
                )
                withContext(Dispatchers.Main) {
                    setSelectedPostId(postId = postId)
                }
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _discoverUiState.postValue(
                    _discoverUiState.value?.copy(
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun deleteSelectedPost() {
        _discoverUiState.value = _discoverUiState.value?.copy(
            isRefreshingShown = true,
            isErrorMessageShown = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val selectedPostId = _discoverUiState.value?.selectedPostId ?: UNSELECTED_POST_ID
                deletePostByIdUseCase(postId = selectedPostId)

                val remainingPostItems = _discoverUiState.value?.postItems?.filter { postItem ->
                    postItem.id != selectedPostId
                }

                _discoverUiState.postValue(
                    _discoverUiState.value?.copy(
                        postItems = remainingPostItems,
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _discoverUiState.postValue(
                    _discoverUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun loadNextPostPage() {
        if (_discoverUiState.value?.isLoadingShown == true) {
            return
        }

        loadNextPostPageJob?.cancel()
        _discoverUiState.value = _discoverUiState.value?.copy(
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        loadNextPostPageJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(LOAD_NEXT_POST_PAGE_DELAY)
                val currentPosts = _discoverUiState.value?.postItems ?: listOf()
                val nextPosts = getFilteredPostPageByUserIdUseCase(
                    userId = _discoverUiState.value?.currentUser?.id,
                    feedFilter = _discoverUiState.value?.feedFilter,
                    postId = _discoverUiState.value?.postItems?.lastOrNull()?.id ?: DEFAULT_POST_ID
                )
                _discoverUiState.postValue(
                    _discoverUiState.value?.copy(
                        postItems = currentPosts + nextPosts,
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                if (errorMessage.isNotBlank()) {
                    _discoverUiState.postValue(
                        _discoverUiState.value?.copy(
                            isRefreshingShown = false,
                            isLoadingShown = false,
                            isErrorMessageShown = true,
                            errorMessage = errorMessage
                        )
                    )
                }
            }
        }
    }

    private fun setDiscoverScreen() {
        setDiscoverScreenJob?.cancel()
        _discoverUiState.value = _discoverUiState.value?.copy(
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        setDiscoverScreenJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUser = getCurrentUserUseCase()
                val categories = _discoverUiState.value?.categories ?: getAllCategoriesUseCase()
                val posts = getFilteredPostPageByUserIdUseCase(
                    userId = currentUser.id,
                    feedFilter = _discoverUiState.value?.feedFilter,
                    postId = DEFAULT_POST_ID
                )
                _discoverUiState.postValue(
                    _discoverUiState.value?.copy(
                        currentUser = currentUser,
                        categories = categories,
                        postItems = posts,
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                if (errorMessage.isNotBlank()) {
                    _discoverUiState.postValue(
                        _discoverUiState.value?.copy(
                            isRefreshingShown = false,
                            isLoadingShown = false,
                            isErrorMessageShown = true,
                            errorMessage = errorMessage
                        )
                    )
                }
            }
        }
    }
}