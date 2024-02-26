package ru.ermakov.creator.presentation.screen.createPost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.PostRequest
import ru.ermakov.creator.domain.useCase.blog.GetTagsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.createPost.PublishPostUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.GetSubscriptionsByCreatorIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class CreatePostViewModel(
    private val getTagsByCreatorIdUseCase: GetTagsByCreatorIdUseCase,
    private val getSubscriptionsByCreatorIdUseCase: GetSubscriptionsByCreatorIdUseCase,
    private val publishPostUseCase: PublishPostUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _createPostUiState = MutableLiveData(CreatePostUiState())
    val createPostUiState: LiveData<CreatePostUiState> = _createPostUiState

    fun setCreatePostScreen(creatorId: String) {
        _createPostUiState.postValue(
            _createPostUiState.value?.copy(
                isLoadingShown = true,
                isErrorMessageShown = false,
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _createPostUiState.postValue(
                    _createPostUiState.value?.copy(
                        creatorId = creatorId,
                        tags = getTagsByCreatorIdUseCase(creatorId = creatorId),
                        subscriptions = getSubscriptionsByCreatorIdUseCase(creatorId = creatorId),
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _createPostUiState.postValue(
                    _createPostUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun refreshCreatePostScreen(creatorId: String) {
        _createPostUiState.value = _createPostUiState.value?.copy(isRefreshingShown = true)
        setCreatePostScreen(creatorId = creatorId)
    }

    fun publishPost(creatorId: String, title: String, content: String) {
        _createPostUiState.postValue(
            _createPostUiState.value?.copy(
                isProgressBarPublishShown = true,
                isErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val selectedTagIds = _createPostUiState.value?.selectedTagIds ?: listOf()
                val requiredSubscriptionIds = _createPostUiState
                    .value
                    ?.requiredSubscriptionIds ?: listOf()
                val imageUrls = _createPostUiState.value?.imageUrls ?: listOf()
                val postRequest = PostRequest(
                    creatorId = creatorId,
                    title = title,
                    content = content,
                    imageUrls = imageUrls,
                    tagIds = selectedTagIds,
                    requiredSubscriptionIds = requiredSubscriptionIds
                )
                publishPostUseCase(postRequest = postRequest)
                _createPostUiState.postValue(
                    _createPostUiState.value?.copy(
                        isPostPublished = true,
                        isProgressBarPublishShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _createPostUiState.postValue(
                    _createPostUiState.value?.copy(
                        isProgressBarPublishShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun changeSelectedTagIds(selectedTagIds: List<Long>) {
        _createPostUiState.value = _createPostUiState.value?.copy(selectedTagIds = selectedTagIds)
    }

    fun changeSelectedSubscriptionIds(selectedSubscriptionIds: List<Long>) {
        _createPostUiState.value = _createPostUiState.value?.copy(
            requiredSubscriptionIds = selectedSubscriptionIds
        )
    }
}