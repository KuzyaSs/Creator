package ru.ermakov.creator.data.mapper

import ru.ermakov.creator.data.remote.model.RemoteCreditGoal
import ru.ermakov.creator.data.remote.model.RemoteCreditGoalRequest
import ru.ermakov.creator.domain.model.CreditGoal
import ru.ermakov.creator.domain.model.CreditGoalRequest
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun RemoteCreditGoal.toCreditGoal(): CreditGoal {
    return CreditGoal(
        id = id,
        creator = remoteCreator.toCreator(),
        targetBalance = targetBalance,
        balance = balance,
        description = description,
        creationDate = ZonedDateTime.parse(
            creationDate,
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.systemDefault())
        ).toLocalDateTime()
    )
}

fun CreditGoalRequest.toRemoteCreditGoalRequest(): RemoteCreditGoalRequest {
    return RemoteCreditGoalRequest(
        creatorId = creatorId,
        targetBalance = targetBalance,
        description = description
    )
}