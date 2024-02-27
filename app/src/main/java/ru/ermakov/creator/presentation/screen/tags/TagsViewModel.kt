package ru.ermakov.creator.presentation.screen.tags

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ermakov.creator.domain.useCase.blog.GetTagsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.tags.DeleteTagByIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class TagsViewModel(
    private val getTagsByCreatorIdUseCase: GetTagsByCreatorIdUseCase,
    private val deleteTagByIdUseCase: DeleteTagByIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _tagsUiState = MutableLiveData(TagsUiState())
    val tagsUiState: LiveData<TagsUiState> = _tagsUiState

    fun setTagsScreen(creatorId: String) {
        _tagsUiState.postValue(
            _tagsUiState.value?.copy(
                isLoading = true,
                isErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _tagsUiState.postValue(
                    _tagsUiState.value?.copy(
                        creatorId = creatorId,
                        tags = getTagsByCreatorIdUseCase(creatorId = creatorId),
                        isRefreshingShown = false,
                        isLoading = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _tagsUiState.postValue(
                    _tagsUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoading = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun refreshTagsScreen(creatorId: String) {
        _tagsUiState.value = _tagsUiState.value?.copy(
            isRefreshingShown = true
        )
        setTagsScreen(creatorId = creatorId)
    }

    fun deleteSelectedTag() {
        _tagsUiState.postValue(
            _tagsUiState.value?.copy(
                isRefreshingShown = true,
                isErrorMessageShown = false
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteTagByIdUseCase(
                    tagId = _tagsUiState.value?.selectedTagId ?: UNSELECTED_TAG_ID
                )
                withContext(Dispatchers.Main) {
                    refreshTagsScreen(creatorId = _tagsUiState.value?.creatorId ?: "")
                }
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _tagsUiState.postValue(
                    _tagsUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoading = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun setSelectedTagId(tagId: Long) {
        _tagsUiState.value = _tagsUiState.value?.copy(
            selectedTagId = tagId
        )
    }

    fun clearErrorMessage() {
        _tagsUiState.value = _tagsUiState.value?.copy(
            isErrorMessageShown = false,
            errorMessage = ""
        )
    }
}
