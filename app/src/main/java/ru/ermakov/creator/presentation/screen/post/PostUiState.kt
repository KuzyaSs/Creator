package ru.ermakov.creator.presentation.screen.post

import ru.ermakov.creator.domain.model.PostItem

data class PostUiState(
    val currentUserId: String = "",
    val postItem: PostItem? = null,
    val isPostDeleted: Boolean = false,
    val isRefreshingShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)