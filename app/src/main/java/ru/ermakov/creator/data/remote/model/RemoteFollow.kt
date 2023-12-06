package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteFollow(
    @SerializedName("id")
    val id: Long,
    @SerializedName("user")
    val remoteUser: RemoteUser,
    @SerializedName("creator")
    val remoteCreator: RemoteCreator,
    @SerializedName("startDate")
    val startDate: String
)