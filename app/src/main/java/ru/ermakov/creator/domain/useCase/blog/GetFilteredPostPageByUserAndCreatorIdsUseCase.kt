package ru.ermakov.creator.domain.useCase.blog

import ru.ermakov.creator.domain.exception.UnexpectedValueException
import ru.ermakov.creator.domain.model.BlogFilter
import ru.ermakov.creator.domain.model.PostItem
import ru.ermakov.creator.domain.repository.PostRepository
import ru.ermakov.creator.domain.useCase.shared.GetDateTimeUseCase

class GetFilteredPostPageByUserAndCreatorIdsUseCase(
    private val postRepository: PostRepository,
    private val getDateTimeUseCase: GetDateTimeUseCase
) {
    suspend operator fun invoke(
        userId: String?,
        creatorId: String?,
        blogFilter: BlogFilter?,
        postId: Long
    ): List<PostItem> {
        if (userId == null || creatorId == null || blogFilter == null) {
            throw UnexpectedValueException()
        }
        return postRepository.getFilteredPostPageByUserAndCreatorIds(
            userId = userId,
            creatorId = creatorId,
            blogFilter = blogFilter,
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