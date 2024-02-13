package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteLikeRequest(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("postId")
    val postId: Long,
)
