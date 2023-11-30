package ru.ermakov.creator.presentation.screen.blog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.useCase.blog.GetCreatorByIdUseCase
import ru.ermakov.creator.domain.useCase.blog.IsFollowerUseCase
import ru.ermakov.creator.domain.useCase.blog.IsSubscriberUseCase
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class BlogViewModel(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val isFollowerUseCase: IsFollowerUseCase,
    private val isSubscriberUseCase: IsSubscriberUseCase,
    private val getCreatorByIdUseCase: GetCreatorByIdUseCase,
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
                _blogUiState.postValue(
                    _blogUiState.value?.copy(
                        currentUserId = getCurrentUserIdUseCase(),
                        isFollower = isFollowerUseCase(),
                        isSubscriber = isSubscriberUseCase(),
                        creator = getCreatorByIdUseCase(creatorId = creatorId),
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
                        errorMessage = errorMessage,
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

    fun clearErrorMessage() {
        _blogUiState.value = _blogUiState.value?.copy(
            isErrorMessageShown = false,
            errorMessage = ""
        )
    }
}