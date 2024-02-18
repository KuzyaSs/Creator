package ru.ermakov.creator.presentation.screen.following

import ru.ermakov.creator.domain.model.Category
import ru.ermakov.creator.domain.model.FeedFilter
import ru.ermakov.creator.domain.model.PostItem
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.presentation.screen.following.DefaultFeedFilter.defaultFeedFilter

const val UNSELECTED_POST_ID = -1L

data class FollowingUiState(
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

object DefaultFeedFilter {
    const val ALL_POST_TYPE = "ALL"
    const val AVAILABLE_POST_TYPE = "AVAILABLE"
    val defaultFeedFilter = FeedFilter(ALL_POST_TYPE, listOf())
}