package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteTagRequest(
    @SerializedName("creatorId")
    val creatorId: String,
    @SerializedName("name")
    val name: String
)
