package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteImage(
    @SerializedName("id")
    val id: Long,
    @SerializedName("url")
    val url: String
)
