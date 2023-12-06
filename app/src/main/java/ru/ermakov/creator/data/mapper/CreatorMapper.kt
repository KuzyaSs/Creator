package ru.ermakov.creator.data.mapper

import ru.ermakov.creator.data.remote.model.RemoteCreator
import ru.ermakov.creator.domain.model.Creator

fun RemoteCreator.toCreator(): Creator {
    return Creator(
        user = remoteUser.toUser(),
        categories = categories,
        followerCount = followerCount,
        subscriberCount = subscriberCount,
        postCount = postCount
    )
}
