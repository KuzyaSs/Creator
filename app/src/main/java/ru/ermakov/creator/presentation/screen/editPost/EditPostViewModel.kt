package ru.ermakov.creator.presentation.screen.editPost

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.PostRequest
import ru.ermakov.creator.domain.useCase.blog.GetTagsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.editPost.EditPostUseCase
import ru.ermakov.creator.domain.useCase.following.GetPostByUserAndPostIdsUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.GetSubscriptionsByCreatorIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class EditPostViewModel(
    private val getPostByUserAndPostIdsUseCase: GetPostByUserAndPostIdsUseCase,
    private val getTagsByCreatorIdUseCase: GetTagsByCreatorIdUseCase,
    private val getSubscriptionsByCreatorIdUseCase: GetSubscriptionsByCreatorIdUseCase,
    private val editPostUseCase: EditPostUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _editPostUiState = MutableLiveData(EditPostUiState())
    val editPostUiState: LiveData<EditPostUiState> = _editPostUiState

    fun setEditPostScreen(creatorId: String, postId: Long) {
        _editPostUiState.postValue(
            _editPostUiState.value?.copy(
                isLoadingShown = true,
                isErrorMessageShown = false,
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postItem = getPostByUserAndPostIdsUseCase(userId = creatorId, postId = postId)
                val selectedTagIds = if (_editPostUiState.value?.selectedTagIds.isNullOrEmpty())
                    postItem.tags.map { tag -> tag.id }
                else
                    _editPostUiState.value?.selectedTagIds ?: listOf()

                val requiredSubscriptionIds =
                    if (_editPostUiState.value?.requiredSubscriptionIds.isNullOrEmpty())
                        postItem.requiredSubscriptions.map { subscription -> subscription.id }
                    else
                        _editPostUiState.value?.requiredSubscriptionIds ?: listOf()

                _editPostUiState.postValue(
                    _editPostUiState.value?.copy(
                        creatorId = creatorId,
                        postItem = postItem,
                        tags = getTagsByCreatorIdUseCase(creatorId = creatorId),
                        subscriptions = getSubscriptionsByCreatorIdUseCase(creatorId = creatorId),
                        selectedTagIds = selectedTagIds,
                        requiredSubscriptionIds = requiredSubscriptionIds,
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _editPostUiState.postValue(
                    _editPostUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun refreshEditPostScreen(creatorId: String, postId: Long) {
        _editPostUiState.value = _editPostUiState.value?.copy(isRefreshingShown = true)
        setEditPostScreen(creatorId = creatorId, postId = postId)
    }

    fun editPost(creatorId: String, postId: Long, title: String, content: String) {
        _editPostUiState.postValue(
            _editPostUiState.value?.copy(
                isProgressBarSaveChangesShown = true,
                isErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val selectedTagIds = _editPostUiState.value?.selectedTagIds ?: listOf()
                val requiredSubscriptionIds = _editPostUiState
                    .value
                    ?.requiredSubscriptionIds ?: listOf()
                val imageUrls = _editPostUiState.value?.imageUrls ?: listOf()
                val postRequest = PostRequest(
                    creatorId = creatorId,
                    title = title,
                    content = content,
                    imageUrls = imageUrls,
                    tagIds = selectedTagIds,
                    requiredSubscriptionIds = requiredSubscriptionIds
                )
                editPostUseCase(postId = postId, postRequest = postRequest)
                _editPostUiState.postValue(
                    _editPostUiState.value?.copy(
                        isPostEdited = true,
                        isProgressBarSaveChangesShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _editPostUiState.postValue(
                    _editPostUiState.value?.copy(
                        isProgressBarSaveChangesShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun changeSelectedTagIds(selectedTagIds: List<Long>) {
        _editPostUiState.value = _editPostUiState.value?.copy(selectedTagIds = selectedTagIds)
    }

    fun changeSelectedSubscriptionIds(selectedSubscriptionIds: List<Long>) {
        _editPostUiState.value = _editPostUiState.value?.copy(
            requiredSubscriptionIds = selectedSubscriptionIds
        )
    }
}