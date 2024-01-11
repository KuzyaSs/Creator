package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteUserTransaction(
    @SerializedName("id")
    val id: Long,
    @SerializedName("senderUser")
    val senderRemoteUser: RemoteUser,
    @SerializedName("receiverUser")
    val receiverRemoteUser: RemoteUser,
    @SerializedName("transactionType")
    val remoteTransactionType: RemoteTransactionType,
    @SerializedName("amount")
    val amount: Long,
    @SerializedName("message")
    val message: String,
    @SerializedName("transactionDate")
    val transactionDate: String
)
