package ru.ermakov.creator.presentation.screen.follows

import ru.ermakov.creator.domain.model.Follow

data class FollowsUiState(
    val follows: List<Follow>? = null,
    val lastSearchQuery: String = " ",
    val currentFollowPage: Int = 0,
    val isSearchBarShown: Boolean = false,
    val isRefreshingShown: Boolean = false,
    val isLoadingFollows: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)