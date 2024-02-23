package ru.ermakov.creator.presentation.screen.search.searchPost

import ru.ermakov.creator.domain.model.PostItem
import ru.ermakov.creator.presentation.screen.following.UNSELECTED_POST_ID

data class SearchPostUiState(
    val currentUserId: String = "",
    val postItems: List<PostItem>? = null,
    val selectedPostId: Long = UNSELECTED_POST_ID,
    val lastSearchQuery: String = "",
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)
