package ru.ermakov.creator.presentation.screen.createPost

import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.domain.model.Tag

data class CreatePostUiState(
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
