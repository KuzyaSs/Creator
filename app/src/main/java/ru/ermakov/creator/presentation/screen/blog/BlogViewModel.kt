package ru.ermakov.creator.presentation.screen.blog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.useCase.blog.FollowUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCreatorByUserIdUseCase
import ru.ermakov.creator.domain.useCase.blog.IsFollowerByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.blog.IsSubscriberUseCase
import ru.ermakov.creator.domain.useCase.blog.UnfollowUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class BlogViewModel(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val isFollowerByUserAndCreatorIdsUseCase: IsFollowerByUserAndCreatorIdsUseCase,
    private val followUseCase: FollowUseCase,
    private val unfollowUseCase: UnfollowUseCase,
    private val isSubscriberUseCase: IsSubscriberUseCase,
    private val getCreatorByUserIdUseCase: GetCreatorByUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _blogUiState = MutableLiveData(BlogUiState())
    val blogUiState: LiveData<BlogUiState> = _blogUiState

    fun setBlog(creatorId: String) {
        _blogUiState.postValue(
            _blogUiState.value?.copy(
                isErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUserId = getCurrentUserIdUseCase()
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
                        isRefreshingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _blogUiState.postValue(
                    _blogUiState.value?.copy(
                        isRefreshingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun refreshBlog(creatorId: String) {
        _blogUiState.value = _blogUiState.value?.copy(
            isRefreshingShown = true
        )
        setBlog(creatorId = creatorId)
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

    fun clearErrorMessage() {
        _blogUiState.value = _blogUiState.value?.copy(
            isErrorMessageShown = false,
            errorMessage = ""
        )
    }
}