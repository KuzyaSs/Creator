package ru.ermakov.creator.data.mapper

import ru.ermakov.creator.data.remote.model.RemoteSubscription
import ru.ermakov.creator.data.remote.model.RemoteSubscriptionRequest
import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.domain.model.SubscriptionRequest

fun RemoteSubscription.toSubscription(): Subscription {
    return Subscription(
        id = id,
        creator = remoteCreator.toCreator(),
        title = title,
        description = description,
        price = price
    )
}

fun SubscriptionRequest.toRemoteSubscriptionRequest(): RemoteSubscriptionRequest {
    return RemoteSubscriptionRequest(
        creatorId = creatorId,
        title = title,
        description = description,
        price = price
    )
}
