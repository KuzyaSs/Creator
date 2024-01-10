package ru.ermakov.creator.presentation.screen.follows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.Follow
import ru.ermakov.creator.domain.useCase.follows.SearchFollowPageByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

private const val SEARCH_FOLLOWS_DELAY = 1000L

class FollowsViewModel(
    private val searchFollowPageByUserIdUseCase: SearchFollowPageByUserIdUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _followsUiState = MutableLiveData(FollowsUiState())
    val followsUiState: LiveData<FollowsUiState> = _followsUiState

    private var searchFollowsJob: Job? = null
    private var loadNextFollowPageJob: Job? = null

    init {
        searchFollows(searchQuery = "")
    }

    fun searchFollows(searchQuery: String) {
        if (searchQuery == _followsUiState.value?.lastSearchQuery &&
            _followsUiState.value?.isRefreshingShown == false
        ) {
            return
        }
        searchFollowsJob?.cancel()
        loadNextFollowPageJob?.cancel()

        var newSearchedFollows: List<Follow>? = null
        if (_followsUiState.value?.follows != null) {
            newSearchedFollows = listOf()
        }
        _followsUiState.value = _followsUiState.value?.copy(
            follows = newSearchedFollows,
            lastSearchQuery = searchQuery,
            isRefreshingShown = false,
            isLoadingFollows = true,
            isErrorMessageShown = false
        )
        searchFollowsJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(SEARCH_FOLLOWS_DELAY)
                val follows: List<Follow> = searchFollowPageByUserIdUseCase(
                    searchQuery = searchQuery,
                    page = 0,
                    userId = getCurrentUserIdUseCase()
                )
                _followsUiState.postValue(
                    _followsUiState.value?.copy(
                        follows = follows,
                        currentFollowPage = 0,
                        isRefreshingShown = false,
                        isLoadingFollows = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                if (errorMessage.isNotBlank()) {
                    _followsUiState.postValue(
                        _followsUiState.value?.copy(
                            follows = _followsUiState.value?.follows,
                            isRefreshingShown = false,
                            isLoadingFollows = false,
                            isErrorMessageShown = true,
                            errorMessage = errorMessage
                        )
                    )
                }
            }
        }
    }

    fun refreshFollows() {
        _followsUiState.value = _followsUiState.value?.copy(isRefreshingShown = true)
        searchFollows(searchQuery = _followsUiState.value?.lastSearchQuery ?: "")
    }

    fun loadNextFollowPage(searchQuery: String) {
        if (_followsUiState.value?.isLoadingFollows == true) {
            return
        }
        loadNextFollowPageJob?.cancel()

        _followsUiState.value = _followsUiState.value?.copy(
            isLoadingFollows = true,
            isErrorMessageShown = false
        )
        loadNextFollowPageJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(SEARCH_FOLLOWS_DELAY)
                var nextFollowPage = (_followsUiState.value?.currentFollowPage ?: 0) + 1
                val currentFollows = _followsUiState.value?.follows ?: listOf()
                val followingFollows: List<Follow> = searchFollowPageByUserIdUseCase(
                    searchQuery = searchQuery,
                    page = nextFollowPage,
                    userId = getCurrentUserIdUseCase()
                )
                if (followingFollows.isEmpty()) {
                    nextFollowPage--
                }
                _followsUiState.postValue(
                    _followsUiState.value?.copy(
                        follows = currentFollows + followingFollows,
                        currentFollowPage = nextFollowPage,
                        isLoadingFollows = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                if (errorMessage.isNotBlank()) {
                    _followsUiState.postValue(
                        _followsUiState.value?.copy(
                            follows = _followsUiState.value?.follows,
                            isRefreshingShown = false,
                            isLoadingFollows = false,
                            isErrorMessageShown = true,
                            errorMessage = errorMessage
                        )
                    )
                }
            }
        }
    }

    fun setSearchBar(isSearchBarShown: Boolean) {
        _followsUiState.value = _followsUiState.value?.copy(isSearchBarShown = isSearchBarShown)
    }

    fun clearErrorMessage() {
        _followsUiState.value = _followsUiState.value?.copy(
            isErrorMessageShown = false,
            errorMessage = ""
        )
    }
}