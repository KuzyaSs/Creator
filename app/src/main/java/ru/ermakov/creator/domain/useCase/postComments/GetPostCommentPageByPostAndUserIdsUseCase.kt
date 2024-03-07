package ru.ermakov.creator.domain.useCase.postComments

import ru.ermakov.creator.domain.model.PostCommentItem
import ru.ermakov.creator.domain.repository.PostCommentRepository
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetDateTimeUseCase

class GetPostCommentPageByPostAndUserIdsUseCase(
    private val postCommentRepository: PostCommentRepository,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getDateTimeUseCase: GetDateTimeUseCase
) {
    suspend operator fun invoke(
        postId: Long,
        replyCommentId: Long,
        commentId: Long
    ): List<PostCommentItem> {
        return postCommentRepository.getPostCommentPageByPostAndUserIds(
            postId = postId,
            userId = getCurrentUserIdUseCase(),
            replyCommentId = replyCommentId,
            commentId = commentId
        ).map { comment ->
            PostCommentItem(
                id = comment.id,
                user = comment.user,
                postId = comment.postId,
                replyCommentId = comment.replyCommentId,
                content = comment.content,
                publicationDate = getDateTimeUseCase(comment.publicationDate),
                likeCount = comment.likeCount,
                isLiked = comment.isLiked,
                isEdited = comment.isEdited
            )
        }
    }
}