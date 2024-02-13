package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteTag(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String
)
