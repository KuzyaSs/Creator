package ru.ermakov.creator.presentation.screen.post

import ru.ermakov.creator.data.mapper.INVALID_COMMENT_ID
import ru.ermakov.creator.domain.model.CommentItem
import ru.ermakov.creator.domain.model.PostItem

data class PostUiState(
    val currentUserId: String = "",
    val postItem: PostItem? = null,
    val comments: List<CommentItem>? = null,
    val replyCommentId: Long = INVALID_COMMENT_ID,
    val selectedCommentId: Long = INVALID_COMMENT_ID,
    val isEditCommentMode: Boolean = false,
    val isPostDeleted: Boolean = false,
    val isRefreshingShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)