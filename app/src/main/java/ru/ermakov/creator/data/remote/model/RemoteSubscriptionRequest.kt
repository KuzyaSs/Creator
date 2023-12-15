package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName
import ru.ermakov.creator.domain.model.Creator

data class RemoteSubscriptionRequest(
    @SerializedName("creatorId")
    val creatorId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Long
)
