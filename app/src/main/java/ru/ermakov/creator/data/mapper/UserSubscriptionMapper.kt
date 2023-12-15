package ru.ermakov.creator.data.mapper

import ru.ermakov.creator.data.remote.model.RemoteUserSubscription
import ru.ermakov.creator.data.remote.model.RemoteUserSubscriptionRequest
import ru.ermakov.creator.domain.model.UserSubscription
import ru.ermakov.creator.domain.model.UserSubscriptionRequest
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun RemoteUserSubscription.toUserSubscription(): UserSubscription {
    return UserSubscription(
        id = id,
        subscription = remoteSubscription.toSubscription(),
        user = remoteUser.toUser(),
        startDate = ZonedDateTime.parse(
            startDate,
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.systemDefault())
        ).toLocalDateTime(),
        endDate = ZonedDateTime.parse(
            endDate,
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.systemDefault())
        ).toLocalDateTime()
    )
}

fun UserSubscriptionRequest.toRemoteUserSubscriptionRequest(): RemoteUserSubscriptionRequest {
    return RemoteUserSubscriptionRequest(
        subscriptionId = subscriptionId,
        userId = userId,
        durationInMonths = durationInMonths
    )
}