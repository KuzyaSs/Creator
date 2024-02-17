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
import ru.ermakov.creator.domain.model.Category
import ru.ermakov.creator.domain.useCase.account.SignOutUseCase
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoryInListUseCase
import ru.ermakov.creator.domain.useCase.following.GetAllCategoriesUseCase
import ru.ermakov.creator.domain.useCase.following.GetFilteredFollowingPostPageByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserUseCase
import ru.ermakov.creator.presentation.screen.following.DefaultFeedFilter.defaultFeedFilter
import ru.ermakov.creator.presentation.util.ExceptionHandler

private const val LOAD_NEXT_POST_PAGE_DELAY = 1000L
private const val DEFAULT_POST_ID = Long.MAX_VALUE

class FollowingViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getFilteredFollowingPostPageByUserIdUseCase: GetFilteredFollowingPostPageByUserIdUseCase,
    private val updateCategoryInListUseCase: UpdateCategoryInListUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _followingUiState = MutableLiveData(FollowingUiState())
    val followingUiState: LiveData<FollowingUiState> get() = _followingUiState

    private var loadNextPostPageJob: Job? = null
    private var setFollowingScreenJob: Job? = null

    init {
        setFollowingScreen()
    }

    fun refreshFollowingScreen() {
        _followingUiState.value = _followingUiState.value?.copy(isRefreshingShown = true)
        setFollowingScreen()
    }

    fun resetFeedFilter() {
        _followingUiState.value = _followingUiState.value?.copy(
            feedFilter = defaultFeedFilter,
            categories = _followingUiState.value?.categories?.map { it.copy(isSelected = false) }
        )
    }

    fun changePostTypeFilter(postType: String) {
        _followingUiState.value = _followingUiState.value?.copy(
            feedFilter = _followingUiState.value?.feedFilter?.copy(
                postType = postType
            ) ?: defaultFeedFilter
        )
    }

    fun changeCategoryFilter(changedCategory: Category) {
        val updatedCategories = updateCategoryInListUseCase(
            categories = _followingUiState.value?.categories,
            changedCategory = changedCategory
        )

        val filteredCategoryIds = _followingUiState
            .value?.feedFilter
            ?.categoryIds
            ?.toMutableList() ?: mutableListOf()

        if (changedCategory.isSelected && !filteredCategoryIds.contains(changedCategory.id)) {
            filteredCategoryIds.add(changedCategory.id)
        } else {
            filteredCategoryIds.remove(changedCategory.id)
        }

        _followingUiState.value = _followingUiState.value?.copy(
            feedFilter = _followingUiState.value?.feedFilter?.copy(
                categoryIds = filteredCategoryIds
            ) ?: defaultFeedFilter,
            categories = updatedCategories
        )

        Log.d("MY_TAG", followingUiState.value?.feedFilter?.categoryIds.toString())
        Log.d("MY_TAG", followingUiState.value?.categories?.filter { it.isSelected }.toString())
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

    private fun setFollowingScreen() {
        setFollowingScreenJob?.cancel()
        _followingUiState.value = _followingUiState.value?.copy(
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        setFollowingScreenJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentUser = getCurrentUserUseCase()
                val categories = _followingUiState.value?.categories ?: getAllCategoriesUseCase()
                val posts = getFilteredFollowingPostPageByUserIdUseCase(
                    userId = currentUser.id,
                    feedFilter = _followingUiState.value?.feedFilter,
                    postId = DEFAULT_POST_ID
                )
                _followingUiState.postValue(
                    _followingUiState.value?.copy(
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