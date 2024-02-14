package ru.ermakov.creator.presentation.screen.following

import ru.ermakov.creator.domain.model.FeedFilter
import ru.ermakov.creator.domain.model.PostItem
import ru.ermakov.creator.domain.model.User

private const val DEFAULT_POST_TYPE_VALUE = "ALL"

data class FollowingUiState(
    val currentUser: User? = null,
    val feedFilter: FeedFilter = FeedFilter(DEFAULT_POST_TYPE_VALUE, listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)),
    val posts: List<PostItem>? = null,
    val isRefreshingShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)