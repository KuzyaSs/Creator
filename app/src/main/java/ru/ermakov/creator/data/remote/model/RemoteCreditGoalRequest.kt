package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteCreditGoalRequest(
    @SerializedName("creatorId")
    val creatorId: String,
    @SerializedName("targetBalance")
    val targetBalance: Long,
    @SerializedName("description")
    val description: String
)