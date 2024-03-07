package ru.ermakov.creator.domain.useCase.postComments

import ru.ermakov.creator.domain.exception.UnexpectedValueException
import ru.ermakov.creator.domain.model.PostCommentItem
import ru.ermakov.creator.domain.repository.PostCommentRepository
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetDateTimeUseCase
import ru.ermakov.creator.presentation.screen.postComments.UNSELECTED_COMMENT_ID

class GetPostCommentByCommentAndUserIdsUseCase(
    private val postCommentRepository: PostCommentRepository,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getDateTimeUseCase: GetDateTimeUseCase
) {
    suspend operator fun invoke(postCommentId: Long): PostCommentItem {
        if (postCommentId == UNSELECTED_COMMENT_ID) {
            throw UnexpectedValueException()
        }
        val postComment = postCommentRepository.getPostCommentByCommentAndUserIds(
            postCommentId = postCommentId,
            userId = getCurrentUserIdUseCase()
        )
        return PostCommentItem(
            id = postComment.id,
            user = postComment.user,
            postId = postComment.postId,
            replyCommentId = postComment.replyCommentId,
            content = postComment.content,
            publicationDate = getDateTimeUseCase(postComment.publicationDate),
            likeCount = postComment.likeCount,
            isLiked = postComment.isLiked,
            isEdited = postComment.isEdited
        )
    }
}
