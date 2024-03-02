package ru.ermakov.creator.presentation.screen.createPost

import ru.ermakov.creator.domain.model.Tag

interface TagSelectorSource {
    fun getTags(): List<Tag>?

    fun getSelectedTagIds(): List<Long>

    fun changeSelectedTagIds(selectedTagIds: List<Long>)

    fun navigateToTagsFragment()
}