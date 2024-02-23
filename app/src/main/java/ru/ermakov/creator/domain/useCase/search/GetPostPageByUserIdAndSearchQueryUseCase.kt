package ru.ermakov.creator.domain.useCase.search

import ru.ermakov.creator.domain.exception.UnexpectedValueException
import ru.ermakov.creator.domain.model.PostItem
import ru.ermakov.creator.domain.repository.PostRepository
import ru.ermakov.creator.domain.useCase.shared.GetDateTimeUseCase

class GetPostPageByUserIdAndSearchQueryUseCase(
    private val postRepository: PostRepository,
    private val getDateTimeUseCase: GetDateTimeUseCase
) {
    suspend operator fun invoke(
        userId: String?,
        searchQuery: String,
        postId: Long
    ): List<PostItem> {
        if (userId == null) {
            throw UnexpectedValueException()
        }
        return postRepository.getPostPageByUserIdAndSearchQuery(
            userId = userId,
            searchQuery = searchQuery,
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