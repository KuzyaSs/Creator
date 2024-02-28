package ru.ermakov.creator.presentation.screen.editTag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.TagRequest
import ru.ermakov.creator.domain.useCase.editTag.EditTagUseCase
import ru.ermakov.creator.domain.useCase.editTag.GetTagByIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class EditTagViewModel(
    private val getTagByIdUseCase: GetTagByIdUseCase,
    private val editTagUseCase: EditTagUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _editTagUiState = MutableLiveData(EditTagUiState())
    val editTagUiState: LiveData<EditTagUiState> = _editTagUiState

    fun setEditTagScreen(tagId: Long) {
        _editTagUiState.value = _editTagUiState.value?.copy(
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _editTagUiState.postValue(
                    _editTagUiState.value?.copy(
                        tag = getTagByIdUseCase(tagId = tagId),
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _editTagUiState.postValue(
                    _editTagUiState.value?.copy(
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    fun refreshEditTagScreen(tagId: Long) {
        _editTagUiState.value = _editTagUiState.value?.copy(
            isRefreshingShown = true
        )
        setEditTagScreen(tagId = tagId)
    }

    fun editTag(tagId: Long, name: String) {
        _editTagUiState.postValue(
            _editTagUiState.value?.copy(
                isProgressBarSaveChangesShown = true,
                isErrorMessageShown = false,
            )
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tagRequest = TagRequest(creatorId = getCurrentUserIdUseCase(), name = name)
                editTagUseCase(tagId = tagId, tagRequest = tagRequest)
                _editTagUiState.postValue(
                    _editTagUiState.value?.copy(
                        isTagEdited = true,
                        isProgressBarSaveChangesShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _editTagUiState.postValue(
                    _editTagUiState.value?.copy(
                        isProgressBarSaveChangesShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }
}