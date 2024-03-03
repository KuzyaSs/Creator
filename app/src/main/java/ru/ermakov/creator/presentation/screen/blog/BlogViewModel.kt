package ru.ermakov.creator.presentation.screen.blog

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
import ru.ermakov.creator.domain.model.Tag
import ru.ermakov.creator.domain.useCase.blog.FollowUseCase
import ru.ermakov.creator.domain.useCase.blog.GetFilteredPostPageByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.blog.GetTagsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.blog.IsFollowerByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.blog.IsSubscriberUseCase
import ru.ermakov.creator.domain.useCase.blog.UnfollowUseCase
import ru.ermakov.creator.domain.useCase.following.DeleteLikeFromPostUseCase
import ru.ermakov.creator.domain.useCase.following.DeletePostByIdUseCase
import ru.ermakov.creator.domain.useCase.following.GetPostByUserAndPostIdsUseCase
import ru.ermakov.creator.domain.useCase.following.InsertLikeToPostUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCreatorByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.screen.blog.DefaultBlogFilter.defaultBlogFilter
import ru.ermakov.creator.presentation.screen.following.UNSELECTED_POST_ID
import ru.ermakov.creator.presentation.util.ExceptionHandler

private const val LOAD_NEXT_POST_PAGE_DELAY = 1000L
private const val DEFAULT_POST_ID = Long.MAX_VALUE

class BlogViewModel(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val isFollowerByUserAndCreatorIdsUseCase: IsFollowerByUserAndCreatorIdsUseCase,
    private val followUseCase: FollowUseCase,
    private val unfollowUseCase: UnfollowUseCase,
    private val isSubscriberUseCase: IsSubscriberUseCase,
    private val getCreatorByUserIdUseCase: GetCreatorByUserIdUseCase,
    private val getFilteredPostPageByUserAndCreatorIdsUseCase: GetFilteredPostPageByUserAndCreatorIdsUseCase,
    private val getTagsByCreatorId: GetTagsByCreatorIdUseCase,
    private val deletePostByIdUseCase: DeletePostByIdUseCase,
    private val getPostByUserAndPostIdsUseCase: GetPostByUserAndPostIdsUseCase,
    private val insertLikeToPostUseCase: InsertLikeToPostUseCase,
    private val deleteLikeFromPostUseCase: DeleteLikeFromPostUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _blogUiState = MutableLiveData(BlogUiState())
    val blogUiState: LiveData<BlogUiState> = _blogUiState

    private var loadNextPostPageJob: Job? = null

    fun setBlogScreen(creatorId: String) {
        _blogUiState.postValue(
            _blogUiState.value?.copy(
                isLoadingShown = true,
                isErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUserId = getCurrentUserIdUseCase()
                val tags = getTagsByCreatorId(creatorId = creatorId)
                val posts = getFilteredPostPageByUserAndCreatorIdsUseCase(
                    userId = currentUserId,
                    creatorId = creatorId,
                    blogFilter = _blogUiState.value?.blogFilter,
                    postId = DEFAULT_POST_ID
                )
                _blogUiState.postValue(
                    _blogUiState.value?.copy(
                        currentUserId = currentUserId,
                        isFollower = isFollowerByUserAndCreatorIdsUseCase(
                            userId = currentUserId,
                            creatorId = creatorId
                        ),
                        isSubscriber = isSubscriberUseCase(
                            userId = currentUserId,
                            creatorId = creatorId
                        ),
                        creator = getCreatorByUserIdUseCase(userId = creatorId),
                        tags = tags,
                        postItems = posts,
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _blogUiState.postValue(
                    _blogUiState.value?.copy(
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
        if (_blogUiState.value?.isLoadingShown == true) {
            return
        }

        loadNextPostPageJob?.cancel()
        _blogUiState.value = _blogUiState.value?.copy(
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        loadNextPostPageJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(LOAD_NEXT_POST_PAGE_DELAY)
                val currentPosts = _blogUiState.value?.postItems ?: listOf()
                val nextPosts = getFilteredPostPageByUserAndCreatorIdsUseCase(
                    userId = _blogUiState.value?.currentUserId,
                    creatorId = _blogUiState.value?.creator?.user?.id,
                    blogFilter = _blogUiState.value?.blogFilter,
                    postId = _blogUiState.value?.postItems?.lastOrNull()?.id ?: DEFAULT_POST_ID
                )
                _blogUiState.postValue(
                    _blogUiState.value?.copy(
                        postItems = currentPosts + nextPosts,
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                if (errorMessage.isNotBlank()) {
                    _blogUiState.postValue(
                        _blogUiState.value?.copy(
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

    fun refreshBlogScreen(creatorId: String) {
        _blogUiState.value = _blogUiState.value?.copy(
            isRefreshingShown = true
        )
        setBlogScreen(creatorId = creatorId)
    }

    fun follow() {
        _blogUiState.value = _blogUiState.value?.copy(
            isFollower = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                followUseCase(
                    userId = blogUiState.value?.currentUserId,
                    creatorId = blogUiState.value?.creator?.user?.id
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _blogUiState.postValue(
                    _blogUiState.value?.copy(
                        isFollower = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun unfollow() {
        _blogUiState.value = _blogUiState.value?.copy(
            isFollower = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                unfollowUseCase(
                    userId = blogUiState.value?.currentUserId,
                    creatorId = blogUiState.value?.creator?.user?.id
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _blogUiState.postValue(
                    _blogUiState.value?.copy(
                        isFollower = true,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun resetBlogFilter() {
        _blogUiState.value = _blogUiState.value?.copy(blogFilter = defaultBlogFilter)
    }

    fun changePostTypeFilter(postType: String) {
        _blogUiState.value = _blogUiState.value?.copy(
            blogFilter = _blogUiState.value?.blogFilter?.copy(
                postType = postType
            ) ?: defaultBlogFilter
        )
    }

    fun changeTagFilter(selectedTagIds: List<Long>) {
        _blogUiState.value = _blogUiState.value?.copy(
            blogFilter = _blogUiState.value?.blogFilter?.copy(
                tagIds = selectedTagIds
            ) ?: defaultBlogFilter
        )
    }

    fun insertLikeToPost(postId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                insertLikeToPostUseCase(
                    userId = _blogUiState.value?.currentUserId,
                    postId = postId
                )
                withContext(Dispatchers.Main) {
                    setSelectedPostId(postId = postId)
                }
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _blogUiState.postValue(
                    _blogUiState.value?.copy(
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
                    userId = _blogUiState.value?.currentUserId,
                    postId = postId
                )
                withContext(Dispatchers.Main) {
                    setSelectedPostId(postId = postId)
                }
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _blogUiState.postValue(
                    _blogUiState.value?.copy(
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun setSelectedPostId(postId: Long) {
        _blogUiState.value = _blogUiState.value?.copy(selectedPostId = postId)
        updateSelectedPostId()
    }

    fun updateSelectedPostId() {
        val userId = _blogUiState.value?.currentUserId
        val selectedPostId = _blogUiState.value?.selectedPostId ?: UNSELECTED_POST_ID
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedPostItem = getPostByUserAndPostIdsUseCase(
                    userId = userId,
                    postId = selectedPostId
                )
                val postItems = _blogUiState.value?.postItems?.map { postItem ->
                    if (postItem.id == selectedPostId) {
                        updatedPostItem
                    } else {
                        postItem
                    }
                }
                _blogUiState.postValue(_blogUiState.value?.copy(postItems = postItems))
            } catch (exception: Exception) {
                if (exception is PostNotFoundException) {
                    val remainingPostItems = _blogUiState.value?.postItems?.filter { postItem ->
                        postItem.id != selectedPostId
                    }
                    _blogUiState.postValue(_blogUiState.value?.copy(postItems = remainingPostItems))
                }
            }
        }
    }

    fun deleteSelectedPost() {
        _blogUiState.value = _blogUiState.value?.copy(isErrorMessageShown = false)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val selectedPostId = _blogUiState.value?.selectedPostId ?: UNSELECTED_POST_ID
                deletePostByIdUseCase(postId = selectedPostId)

                val remainingPostItems = _blogUiState.value?.postItems?.filter { postItem ->
                    postItem.id != selectedPostId
                }

                _blogUiState.postValue(
                    _blogUiState.value?.copy(
                        postItems = remainingPostItems,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _blogUiState.postValue(
                    _blogUiState.value?.copy(
                        isLoadingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun clearErrorMessage() {
        _blogUiState.value = _blogUiState.value?.copy(
            isErrorMessageShown = false,
            errorMessage = ""
        )
    }
}