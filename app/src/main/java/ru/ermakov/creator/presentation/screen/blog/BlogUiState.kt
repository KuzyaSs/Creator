package ru.ermakov.creator.presentation.screen.blog

import ru.ermakov.creator.domain.model.BlogFilter
import ru.ermakov.creator.domain.model.Creator
import ru.ermakov.creator.domain.model.PostItem
import ru.ermakov.creator.domain.model.Tag
import ru.ermakov.creator.presentation.screen.blog.DefaultBlogFilter.defaultBlogFilter
import ru.ermakov.creator.presentation.screen.following.DefaultFeedFilter.ALL_POST_TYPE
import ru.ermakov.creator.presentation.screen.following.UNSELECTED_POST_ID

data class BlogUiState(
    val currentUserId: String = "",
    val isFollower: Boolean = false,
    val isSubscriber: Boolean = false,
    val creator: Creator? = null,
    val blogFilter: BlogFilter = defaultBlogFilter,
    val tags: List<Tag>? = null,
    val postItems: List<PostItem>? = null,
    val selectedPostId: Long = UNSELECTED_POST_ID,
    val isRefreshingShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)

object DefaultBlogFilter {
    val defaultBlogFilter = BlogFilter(postType = ALL_POST_TYPE, listOf())
}