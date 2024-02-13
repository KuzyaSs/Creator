package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemotePostRequest(
    @SerializedName("creatorId")
    val creatorId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("imageUrls")
    val imageUrls: List<String>,
    @SerializedName("tagIds")
    val tagIds: List<Long>,
    @SerializedName("requiredSubscriptionIds")
    val requiredSubscriptionIds: List<Long>,
)
