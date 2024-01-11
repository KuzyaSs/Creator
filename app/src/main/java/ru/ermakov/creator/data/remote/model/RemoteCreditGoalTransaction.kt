package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteCreditGoalTransaction(
    @SerializedName("id")
    val id: Long,
    @SerializedName("creditGoal")
    val remoteCreditGoal: RemoteCreditGoal,
    @SerializedName("user")
    val remoteUser: RemoteUser,
    @SerializedName("transactionType")
    val remoteTransactionType: RemoteTransactionType,
    @SerializedName("amount")
    val amount: Long,
    @SerializedName("message")
    val message: String,
    @SerializedName("transactionDate")
    val transactionDate: String
)
