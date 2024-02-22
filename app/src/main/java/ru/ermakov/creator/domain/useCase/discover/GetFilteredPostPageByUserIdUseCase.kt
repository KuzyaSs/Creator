package ru.ermakov.creator.domain.useCase.discover

import ru.ermakov.creator.domain.exception.UnexpectedValueException
import ru.ermakov.creator.domain.model.FeedFilter
import ru.ermakov.creator.domain.model.PostItem
import ru.ermakov.creator.domain.repository.PostRepository
import ru.ermakov.creator.domain.useCase.shared.GetDateTimeUseCase

class GetFilteredPostPageByUserIdUseCase(
    private val postRepository: PostRepository,
    private val getDateTimeUseCase: GetDateTimeUseCase
) {
    suspend operator fun invoke(
        userId: String?,
        feedFilter: FeedFilter?,
        postId: Long,
    ): List<PostItem> {
        if (userId == null || feedFilter == null) {
            throw UnexpectedValueException()
        }
        return postRepository.getFilteredPostPageByUserId(
            userId = userId,
            feedFilter = feedFilter,
            postId = postId
        ).map { post ->
            PostItem(
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
}