package ru.ermakov.creator.presentation.screen.search.searchCreator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.Creator
import ru.ermakov.creator.domain.useCase.search.SearchCreatorPageBySearchQueryUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

private const val SEARCH_CREATORS_DELAY = 1000L

class SearchCreatorViewModel(
    private val searchCreatorPageBySearchQueryUseCase: SearchCreatorPageBySearchQueryUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _searchCreatorUiState = MutableLiveData(SearchCreatorUiState())
    val searchCreatorUiState: LiveData<SearchCreatorUiState> = _searchCreatorUiState

    private var searchCreatorsJob: Job? = null
    private var loadNextCreatorPageJob: Job? = null

    fun searchCreators(searchQuery: String) {
        if (searchQuery == _searchCreatorUiState.value?.lastSearchQuery) {
            return
        }

        searchCreatorsJob?.cancel()
        loadNextCreatorPageJob?.cancel()
        if (searchQuery.isBlank()) {
            resetSearchCreatorUiState()
            return
        }

        _searchCreatorUiState.value = _searchCreatorUiState.value?.copy(
            creators = listOf(),
            lastSearchQuery = searchQuery,
            isLoadingCreators = true,
            isErrorMessageShown = false
        )
        searchCreatorsJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(SEARCH_CREATORS_DELAY)
                val creators: List<Creator> = searchCreatorPageBySearchQueryUseCase(
                    searchQuery = searchQuery,
                    page = 0
                )
                _searchCreatorUiState.postValue(
                    _searchCreatorUiState.value?.copy(
                        creators = creators,
                        currentCreatorPage = 0,
                        isLoadingCreators = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                if (errorMessage.isNotBlank()) {
                    _searchCreatorUiState.postValue(
                        _searchCreatorUiState.value?.copy(
                            creators = listOf(),
                            isLoadingCreators = false,
                            isErrorMessageShown = true,
                            errorMessage = errorMessage
                        )
                    )
                }
            }
        }
    }

    fun loadNextCreatorPage(searchQuery: String) {
        if (_searchCreatorUiState.value?.isLoadingCreators == true) {
            return
        }

        loadNextCreatorPageJob?.cancel()
        _searchCreatorUiState.value = _searchCreatorUiState.value?.copy(
            isLoadingCreators = true,
            isErrorMessageShown = false
        )
        loadNextCreatorPageJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(SEARCH_CREATORS_DELAY)
                var nextCreatorPage = (_searchCreatorUiState.value?.currentCreatorPage ?: 0) + 1
                val currentCreators = _searchCreatorUiState.value?.creators ?: listOf()
                val followingCreators: List<Creator> = searchCreatorPageBySearchQueryUseCase(
                    searchQuery = searchQuery,
                    page = nextCreatorPage
                )
                if (followingCreators.isEmpty()) {
                    nextCreatorPage--
                }
                _searchCreatorUiState.postValue(
                    _searchCreatorUiState.value?.copy(
                        creators = currentCreators + followingCreators,
                        currentCreatorPage = nextCreatorPage,
                        isLoadingCreators = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                if (errorMessage.isNotBlank()) {
                    _searchCreatorUiState.postValue(
                        _searchCreatorUiState.value?.copy(
                            creators = listOf(),
                            isLoadingCreators = false,
                            isErrorMessageShown = true,
                            errorMessage = errorMessage
                        )
                    )
                }
            }
        }
    }

    fun clearErrorMessage() {
        _searchCreatorUiState.value = _searchCreatorUiState.value?.copy(
            isErrorMessageShown = false,
            errorMessage = ""
        )
    }

    private fun resetSearchCreatorUiState() {
        _searchCreatorUiState.value = _searchCreatorUiState.value?.copy(
            creators = listOf(),
            lastSearchQuery = "",
            currentCreatorPage = 0,
            isLoadingCreators = false,
            isErrorMessageShown = false
        )
    }
}