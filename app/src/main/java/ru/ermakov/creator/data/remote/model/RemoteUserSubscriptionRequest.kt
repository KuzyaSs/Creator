package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteUserSubscriptionRequest(
    @SerializedName("subscriptionId")
    val subscriptionId: Long,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("durationInMonths")
    val durationInMonths: Int
)
