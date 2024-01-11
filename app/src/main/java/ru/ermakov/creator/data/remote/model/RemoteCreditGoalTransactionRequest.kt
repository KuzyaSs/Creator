package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteCreditGoalTransactionRequest(
    @SerializedName("creditGoalId")
    val creditGoalId: Long,
    @SerializedName("senderUserId")
    val senderUserId: String,
    @SerializedName("receiverUserId")
    val receiverUserId: String,
    @SerializedName("transactionTypeId")
    val transactionTypeId: Long,
    @SerializedName("amount")
    val amount: Long,
    @SerializedName("message")
    val message: String
)
