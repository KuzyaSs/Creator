package ru.ermakov.creator.domain.useCase.post

import ru.ermakov.creator.domain.model.CommentItem
import ru.ermakov.creator.domain.repository.PostCommentRepository
import ru.ermakov.creator.domain.useCase.shared.GetDateTimeUseCase

class GetCommentPageByPostAndReplyCommentIdsUseCase(
    private val postCommentRepository: PostCommentRepository,
    private val getDateTimeUseCase: GetDateTimeUseCase
) {
    suspend operator fun invoke(
        postId: Long,
        replyCommentId: Long,
        commentId: Long
    ): List<CommentItem> {
        return postCommentRepository.getCommentPageByPostAndReplyCommentIds(
            postId = postId,
            replyCommentId = replyCommentId,
            commentId = commentId
        ).map { comment ->
            CommentItem(
                id = comment.id,
                user = comment.user,
                postId = comment.postId,
                replyCommentId = comment.replyCommentId,
                content = comment.content,
                publicationDate = getDateTimeUseCase(comment.publicationDate),
                isEdited = comment.isEdited
            )
        }
    }
}
