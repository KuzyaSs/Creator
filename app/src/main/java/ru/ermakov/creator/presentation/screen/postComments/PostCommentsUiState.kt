package ru.ermakov.creator.presentation.screen.postComments

import ru.ermakov.creator.data.mapper.INVALID_COMMENT_ID
import ru.ermakov.creator.domain.model.PostCommentItem

const val UNSELECTED_COMMENT_ID = -1L

data class PostCommentsUiState(
    val postCommentItems: List<PostCommentItem>? = null,
    val selectedPostCommentId: Long = INVALID_COMMENT_ID,
    val isEditCommentMode: Boolean = false,
    val isPostCommentInserted: Boolean = false,
    val isLoadingSendShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)