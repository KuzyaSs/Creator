package ru.ermakov.creator.presentation.screen.createTag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.TagRequest
import ru.ermakov.creator.domain.useCase.createTag.InsertTagUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class CreateTagViewModel(
    private val insertTagUseCase: InsertTagUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _createTagUiState = MutableLiveData(CreateTagUiState())
    val createTagUiState: LiveData<CreateTagUiState> = _createTagUiState

    fun createTag(creatorId: String, name: String) {
        _createTagUiState.value = _createTagUiState.value?.copy(
            isProgressBarCreateShown = true,
            isErrorMessageShown = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tagRequest = TagRequest(creatorId = creatorId, name = name)
                insertTagUseCase(tagRequest = tagRequest)
                _createTagUiState.postValue(
                    _createTagUiState.value?.copy(
                        isTagCreated = true,
                        isProgressBarCreateShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _createTagUiState.postValue(
                    _createTagUiState.value?.copy(
                        isProgressBarCreateShown = false,
                        isErrorMessageShown = true,
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }
}