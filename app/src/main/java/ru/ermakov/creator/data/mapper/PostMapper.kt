package ru.ermakov.creator.data.mapper

import ru.ermakov.creator.data.remote.model.RemoteImage
import ru.ermakov.creator.data.remote.model.RemoteLikeRequest
import ru.ermakov.creator.data.remote.model.RemotePost
import ru.ermakov.creator.data.remote.model.RemotePostRequest
import ru.ermakov.creator.data.remote.model.RemoteTag
import ru.ermakov.creator.data.remote.model.RemoteTagRequest
import ru.ermakov.creator.domain.model.Image
import ru.ermakov.creator.domain.model.LikeRequest
import ru.ermakov.creator.domain.model.Post
import ru.ermakov.creator.domain.model.PostRequest
import ru.ermakov.creator.domain.model.Tag
import ru.ermakov.creator.domain.model.TagRequest
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun RemotePost.toPost(): Post {
    return Post(
        id = id,
        creator = remoteCreator.toCreator(),
        title = title,
        content = content,
        images = remoteImages.map { remoteImage ->
            remoteImage.toImage()
        },
        tags = remoteTags.map { remoteTag ->
            remoteTag.toTag()
        },
        requiredSubscriptions = remoteRequiredSubscriptions.map { remoteSubscription ->
            remoteSubscription.toSubscription()
        },
        likeCount = likeCount,
        commentCount = commentCount,
        publicationDate = ZonedDateTime.parse(
            publicationDate,
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.systemDefault())
        ).toLocalDateTime(),
        isAvailable = isAvailable,
        isLiked = isLiked,
        isEdited = isEdited
    )
}

fun RemoteImage.toImage(): Image {
    return Image(
        id = id,
        url = url
    )
}

fun RemoteTag.toTag(): Tag {
    return Tag(
        id = id,
        name = name
    )
}

fun PostRequest.toRemotePostRequest(): RemotePostRequest {
    return RemotePostRequest(
        creatorId = creatorId,
        title = title,
        content = content,
        imageUrls = imageUrls,
        tagIds = tagIds,
        requiredSubscriptionIds = requiredSubscriptionIds
    )
}

fun LikeRequest.toRemoteLikeRequest(): RemoteLikeRequest {
    return RemoteLikeRequest(userId = userId, postId = postId)
}

fun TagRequest.toRemoteTagRequest(): RemoteTagRequest {
    return RemoteTagRequest(
        creatorId = creatorId,
        name = name
    )
}