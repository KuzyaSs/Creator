package ru.ermakov.creator.presentation.screen.search.searchCreator

import ru.ermakov.creator.domain.model.Creator

data class SearchCreatorUiState(
    val creators: List<Creator>? = null,
    val lastSearchQuery: String = "",
    val currentCreatorPage: Int = 0,
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)