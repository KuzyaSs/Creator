package ru.ermakov.creator.data.mapper

import ru.ermakov.creator.data.remote.model.RemoteFollow
import ru.ermakov.creator.domain.model.Follow
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun RemoteFollow.toFollow(): Follow {
    return Follow(
        id = id,
        user = remoteUser.toUser(),
        creator = remoteCreator.toCreator(),
        startDate = ZonedDateTime.parse(
            startDate,
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.systemDefault())
        ).toLocalDateTime()
    )
}
