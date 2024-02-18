package ru.ermakov.creator.domain.useCase.following

import ru.ermakov.creator.domain.exception.UnexpectedValueException
import ru.ermakov.creator.domain.model.PostItem
import ru.ermakov.creator.domain.repository.PostRepository
import ru.ermakov.creator.domain.useCase.shared.GetDateTimeUseCase
import ru.ermakov.creator.presentation.screen.following.UNSELECTED_POST_ID

class GetPostByUserAndPostIdsUseCase(
    private val postRepository: PostRepository,
    private val getDateTimeUseCase: GetDateTimeUseCase
) {
    suspend operator fun invoke(userId: String?, postId: Long): PostItem {
        if (userId == null || postId == UNSELECTED_POST_ID) {
            throw UnexpectedValueException()
        }
        val post = postRepository.getPostByUserAndPostIds(userId = userId, postId = postId)
        return PostItem(
            id = post.id,
            creator = post.creator,
            title = post.title,
            content = post.content,
            images = post.images,
            tags = post.tags,
            requiredSubscriptions = post.requiredSubscriptions,
            likeCount = post.likeCount,
            commentCount = post.commentCount,
            publicationDate = getDateTimeUseCase(post.publicationDate),
            isAvailable = post.isAvailable,
            isLiked = post.isLiked,
            isEdited = post.isEdited
        )
    }
}
