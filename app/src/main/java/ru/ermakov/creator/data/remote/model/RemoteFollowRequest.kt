package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteFollowRequest(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("creatorId")
    val creatorId: String
)