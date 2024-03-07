package ru.ermakov.creator.data.mapper

import ru.ermakov.creator.data.remote.model.RemotePostComment
import ru.ermakov.creator.data.remote.model.RemotePostCommentLikeRequest
import ru.ermakov.creator.data.remote.model.RemotePostCommentRequest
import ru.ermakov.creator.domain.model.PostComment
import ru.ermakov.creator.domain.model.PostCommentLikeRequest
import ru.ermakov.creator.domain.model.PostCommentRequest
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

const val INVALID_COMMENT_ID = -1L

fun RemotePostComment.toPostComment(): PostComment {
    return PostComment(
        id = id,
        user = remoteUser.toUser(),
        postId = postId,
        replyCommentId = replyCommentId,
        content = content,
        publicationDate = ZonedDateTime.parse(
            publicationDate,
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.systemDefault())
        ).toLocalDateTime(),
        likeCount = likeCount,
        isLiked = isLiked,
        isEdited = isEdited
    )
}

fun PostCommentRequest.toRemotePostCommentRequest(): RemotePostCommentRequest {
    return RemotePostCommentRequest(
        userId = userId,
        postId = postId,
        replyCommentId = if (replyCommentId == INVALID_COMMENT_ID) null else replyCommentId,
        content = content
    )
}

fun PostCommentLikeRequest.toRemotePostCommentLikeRequest(): RemotePostCommentLikeRequest {
    return RemotePostCommentLikeRequest(
        postCommentId = postCommentId,
        userId = userId
    )
}