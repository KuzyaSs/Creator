package ru.ermakov.creator.presentation.screen.blog

import ru.ermakov.creator.domain.model.Creator
import ru.ermakov.creator.domain.model.Follow

data class BlogUiState(
    val currentUserId: String = "",
    // val userSubscriptions: List<UserSubscription>? = null,
    val isFollower: Boolean = false,
    val isSubscriber: Boolean = false, // Change it to List<Subscription>.
    val creator: Creator? = null,
    // val subscriptions: List<Subscription>? = null,
    // val posts: List<Post>? = null,
    // val blogFilter: BlogFilter? = null, // And FeedFilter
    val isRefreshingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)

/*
data class BlogFilter(
    val postType: PostType, // ALL, AVAILABLE
    val tags: List<Tag>,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
*/
