package ru.ermakov.creator.data.mapper

import ru.ermakov.creator.data.remote.model.RemoteFollow
import ru.ermakov.creator.domain.model.Follow
import java.time.LocalDate

fun RemoteFollow.toFollow(): Follow {
    return Follow(
        id = id,
        user = remoteUser.toUser(),
        creator = remoteCreator.toCreator(),
        startDate = LocalDate.parse(startDate)
    )
}
