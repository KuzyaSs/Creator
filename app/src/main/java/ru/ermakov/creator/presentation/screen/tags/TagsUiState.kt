package ru.ermakov.creator.presentation.screen.tags

import ru.ermakov.creator.domain.model.Tag

const val UNSELECTED_TAG_ID: Long = -1L

data class TagsUiState(
    val creatorId: String = "",
    val tags: List<Tag>? = null,
    val selectedTagId: Long = UNSELECTED_TAG_ID,
    val isRefreshingShown: Boolean = false,
    val isLoading: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)