package ru.ermakov.creator.presentation.screen.blog

data class BlogUiState(
    val currentUserId: String = "",
    val isFollower: Boolean = false,
    val isSubscriber: Boolean = false,
    // val userSubscriptions: List<Subscription>? = null,
    // val creator: Creator? = null,
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
