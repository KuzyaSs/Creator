package ru.ermakov.creator.presentation.screen.editPost

import ru.ermakov.creator.domain.model.PostItem
import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.domain.model.Tag

data class EditPostUiState(
    val creatorId: String = "",
    val postItem: PostItem? = null,
    val tags: List<Tag>? = null,
    val subscriptions: List<Subscription>? = null,
    val selectedTagIds: List<Long> = listOf(),
    val requiredSubscriptionIds: List<Long> = listOf(),
    val imageUrls: List<String> = listOf(),
    val isPostEdited: Boolean = false,
    val isRefreshingShown: Boolean = false,
    val isProgressBarSaveChangesShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)