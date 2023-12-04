package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteFollow(
    @SerializedName("id")
    val id: Long,
    @SerializedName("user")
    val user: RemoteUser,
    @SerializedName("creator")
    val creator: RemoteUser,
    @SerializedName("startDate")
    val startDate: String
)