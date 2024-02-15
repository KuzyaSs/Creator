package ru.ermakov.creator.presentation.screen.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.useCase.account.SignOutUseCase
import ru.ermakov.creator.domain.useCase.following.GetFilteredFollowingPostPageByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserUseCase
import ru.ermakov.creator.presentation.screen.following.DefaultFeedFilter.defaultFeedFilter
import ru.ermakov.creator.presentation.util.ExceptionHandler

private const val LOAD_NEXT_POST_PAGE_DELAY = 1000L
private const val DEFAULT_POST_ID = Long.MAX_VALUE

class FollowingViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getFilteredFollowingPostPageByUserIdUseCase: GetFilteredFollowingPostPageByUserIdUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _followingUiState = MutableLiveData(FollowingUiState())
    val followingUiState: LiveData<FollowingUiState> get() = _followingUiState

    private var loadNextPostPageJob: Job? = null

    init {
        setFollowingScreen()
    }

    fun refreshFollowingScreen() {
        _followingUiState.value = _followingUiState.value?.copy(isRefreshingShown = true)
        setFollowingScreen()
    }

    fun loadNextPostPage() {
        if (_followingUiState.value?.isLoadingShown == true) {
            return
        }

        loadNextPostPageJob?.cancel()
        _followingUiState.value = _followingUiState.value?.copy(
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        loadNextPostPageJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(LOAD_NEXT_POST_PAGE_DELAY)
                val currentPosts = _followingUiState.value?.postItems ?: listOf()
                val nextPosts = getFilteredFollowingPostPageByUserIdUseCase(
                    userId = _followingUiState.value?.currentUser?.id,
                    feedFilter = _followingUiState.value?.feedFilter,
                    postId = _followingUiState.value?.postItems?.last()?.id ?: DEFAULT_POST_ID
                )
                _followingUiState.postValue(
                    _followingUiState.value?.copy(
                        postItems = currentPosts + nextPosts,
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _followingUiState.postValue(
                    _followingUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun changePostTypeFilter(postType: String) {
        _followingUiState.value = _followingUiState.value?.copy(
            feedFilter = _followingUiState.value?.feedFilter?.copy(
                postType = postType
            ) ?: defaultFeedFilter
        )
    }

    fun resetFeedFilter() {
        _followingUiState.value = _followingUiState.value?.copy(feedFilter = defaultFeedFilter)
    }

    fun signOut() {
        signOutUseCase()
    }

    fun clearErrorMessage() {
        _followingUiState.value = _followingUiState.value?.copy(
            isErrorMessageShown = false,
            errorMessage = ""
        )
    }

    private fun setFollowingScreen() {
        _followingUiState.value = _followingUiState.value?.copy(
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUser = getCurrentUserUseCase()
                val posts = getFilteredFollowingPostPageByUserIdUseCase(
                    userId = currentUser.id,
                    feedFilter = _followingUiState.value?.feedFilter,
                    postId = DEFAULT_POST_ID
                )
                Log.d("MY_TAG", posts.map { it.id }.toString())
                _followingUiState.postValue(
                    _followingUiState.value?.copy(
                        currentUser = currentUser,
                        postItems = posts,
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _followingUiState.postValue(
                    _followingUiState.value?.copy(
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