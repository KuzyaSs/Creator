package ru.ermakov.creator.data.mapper

import ru.ermakov.creator.data.remote.model.RemoteComment
import ru.ermakov.creator.data.remote.model.RemoteCommentRequest
import ru.ermakov.creator.domain.model.Comment
import ru.ermakov.creator.domain.model.CommentRequest
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

const val INVALID_COMMENT_ID = -1L

fun RemoteComment.toComment(): Comment {
    return Comment(
        id = id,
        user = remoteUser.toUser(),
        postId = postId,
        replyCommentId = replyCommentId,
        content = content,
        publicationDate = ZonedDateTime.parse(
            publicationDate,
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.systemDefault())
        ).toLocalDateTime(),
        isEdited = isEdited
    )
}

fun CommentRequest.toRemoteCommentRequest(): RemoteCommentRequest {
    return RemoteCommentRequest(
        userId = userId,
        postId = postId,
        replyCommentId = if (replyCommentId == INVALID_COMMENT_ID) null else replyCommentId,
        content = content
    )
}