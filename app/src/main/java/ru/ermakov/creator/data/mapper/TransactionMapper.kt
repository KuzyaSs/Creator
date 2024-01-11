package ru.ermakov.creator.data.mapper

import ru.ermakov.creator.data.remote.model.RemoteCreditGoalTransaction
import ru.ermakov.creator.data.remote.model.RemoteCreditGoalTransactionRequest
import ru.ermakov.creator.data.remote.model.RemoteTransactionType
import ru.ermakov.creator.data.remote.model.RemoteUserTransaction
import ru.ermakov.creator.data.remote.model.RemoteUserTransactionRequest
import ru.ermakov.creator.domain.model.CreditGoalTransaction
import ru.ermakov.creator.domain.model.CreditGoalTransactionRequest
import ru.ermakov.creator.domain.model.TransactionType
import ru.ermakov.creator.domain.model.UserTransaction
import ru.ermakov.creator.domain.model.UserTransactionRequest
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun RemoteUserTransaction.toUserTransaction(): UserTransaction {
    return UserTransaction(
        id = id,
        senderUser = senderRemoteUser.toUser(),
        receiverUser = receiverRemoteUser.toUser(),
        transactionType = remoteTransactionType.toTransactionType(),
        amount = amount,
        message = message,
        transactionDate = ZonedDateTime.parse(
            transactionDate,
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.systemDefault())
        ).toLocalDateTime()
    )
}

fun UserTransactionRequest.toRemoteUserTransactionRequest(): RemoteUserTransactionRequest {
    return RemoteUserTransactionRequest(
        senderUserId = senderUserId,
        receiverUserId = receiverUserId,
        transactionTypeId = transactionTypeId,
        amount = amount,
        message = message
    )
}

fun RemoteCreditGoalTransaction.toCreditGoalTransaction(): CreditGoalTransaction {
    return CreditGoalTransaction(
        id = id,
        creditGoal = remoteCreditGoal.toCreditGoal(),
        user = remoteUser.toUser(),
        transactionType = remoteTransactionType.toTransactionType(),
        amount = amount,
        message = message,
        transactionDate = ZonedDateTime.parse(
            transactionDate,
            DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.systemDefault())
        ).toLocalDateTime()
    )
}

fun CreditGoalTransactionRequest.toRemoteCreditGoalTransactionRequest(): RemoteCreditGoalTransactionRequest {
    return RemoteCreditGoalTransactionRequest(
        creditGoalId = creditGoalId,
        senderUserId = senderUserId,
        receiverUserId = receiverUserId,
        transactionTypeId = transactionTypeId,
        amount = amount,
        message = message
    )
}

fun RemoteTransactionType.toTransactionType(): TransactionType {
    return TransactionType(
        id = id,
        name = name
    )
}