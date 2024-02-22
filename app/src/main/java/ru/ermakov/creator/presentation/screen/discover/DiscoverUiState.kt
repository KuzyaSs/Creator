package ru.ermakov.creator.presentation.screen.discover

import ru.ermakov.creator.domain.model.Category
import ru.ermakov.creator.domain.model.FeedFilter
import ru.ermakov.creator.domain.model.PostItem
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.presentation.screen.following.DefaultFeedFilter.defaultFeedFilter
import ru.ermakov.creator.presentation.screen.following.UNSELECTED_POST_ID

data class DiscoverUiState(
    val currentUser: User? = null,
    val feedFilter: FeedFilter = defaultFeedFilter,
    val categories: List<Category>? = null,
    val postItems: List<PostItem>? = null,
    val selectedPostId: Long = UNSELECTED_POST_ID,
    val isRefreshingShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)