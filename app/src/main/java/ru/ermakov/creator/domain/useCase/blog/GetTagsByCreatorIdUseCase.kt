package ru.ermakov.creator.domain.useCase.blog

import kotlinx.coroutines.delay
import ru.ermakov.creator.domain.model.Tag

class GetTagsByCreatorIdUseCase {
    suspend operator fun invoke(creatorId: String): List<Tag> {
        delay(1500L)
        return FakeTags.fakeTags
    }
}

object FakeTags {
    val fakeTags = listOf(
        Tag(1L, "Tag1"),
        Tag(2L, "Tag2"),
        Tag(3L, "Tag3"),
        Tag(4L, "Tag4"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag66"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag66"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag66"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag5"),
        Tag(5L, "Tag77"),
    )
}