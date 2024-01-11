package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteCreditGoal(
    @SerializedName("id")
    val id: Long,
    @SerializedName("creator")
    val remoteCreator: RemoteCreator,
    @SerializedName("targetBalance")
    val targetBalance: Long,
    @SerializedName("balance")
    val balance: Long,
    @SerializedName("description")
    val description: String,
    @SerializedName("creationDate")
    val creationDate: String
)