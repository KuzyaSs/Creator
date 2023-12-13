package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteSubscription(
    @SerializedName("id")
    val id: Long,
    @SerializedName("creator")
    val remoteCreator: RemoteCreator,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Int
)
