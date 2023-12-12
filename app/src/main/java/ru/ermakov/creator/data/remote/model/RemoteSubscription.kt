package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName
import ru.ermakov.creator.domain.model.Creator

data class RemoteSubscription(
    @SerializedName("id")
    val id: Long,
    @SerializedName("creator")
    val creator: Creator,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Int
)
