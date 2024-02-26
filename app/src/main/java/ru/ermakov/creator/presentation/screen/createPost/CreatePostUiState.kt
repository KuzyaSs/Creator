package ru.ermakov.creator.presentation.screen.createPost

import ru.ermakov.creator.domain.model.Creator
import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.domain.model.Tag
import ru.ermakov.creator.domain.model.User
import java.time.LocalDateTime

data class CreatePostUiState(
    val creatorId: String = "",
    val tags: List<Tag>? = null,
    val subscriptions: List<Subscription>? = null,
    val selectedTagIds: List<Long> = listOf(),
    val requiredSubscriptionIds: List<Long> = listOf(),
    val imageUrls: List<String> = listOf(),
    val isPostPublished: Boolean = false,
    val isRefreshingShown: Boolean = false,
    val isProgressBarPublishShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)

object FakeItems {
    private const val INVALID_SUBSCRIPTION_ID = -1L
    private const val INVALID_TAG_ID = -1L

    val fakeSubscription = Subscription(
        id = INVALID_SUBSCRIPTION_ID,
        creator = Creator(
            user = User(
                id = "",
                username = "",
                email = "",
                bio = "",
                profileAvatarUrl = "",
                profileBackgroundUrl = "",
                isModerator = false,
                registrationDate = LocalDateTime.now()
            ),
            categories = listOf(),
            followerCount = 0L,
            subscriberCount = 0L,
            postCount = 0L
        ),
        title = "",
        description = "",
        price = 0L
    )

    val fakeTag = Tag(id = INVALID_TAG_ID, name = "")
}