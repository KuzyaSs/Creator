package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteUserSubscription(
    @SerializedName("id")
    val id: Long,
    @SerializedName("subscription")
    val remoteSubscription: RemoteSubscription,
    @SerializedName("user")
    val remoteUser: RemoteUser,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("endDate")
    val endDate: String
)
