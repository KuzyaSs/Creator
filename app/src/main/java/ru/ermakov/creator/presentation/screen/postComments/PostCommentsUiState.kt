package ru.ermakov.creator.presentation.screen.postComments

import ru.ermakov.creator.domain.model.PostCommentItem

const val UNSELECTED_COMMENT_ID = -1L

data class PostCommentsUiState(
    val currentUserId: String = "",
    val postCommentItems: List<PostCommentItem>? = null,
    val selectedPostCommentItem: PostCommentItem? = null,
    val editedPostCommentItem: PostCommentItem? = null,
    val isEditCommentMode: Boolean = false,
    val isPostCommentSent: Boolean = false,
    val isPostCommentEdited: Boolean = false,
    val isLoadingSendShown: Boolean = false,
    val isRefreshingShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isLoadingDialogShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)