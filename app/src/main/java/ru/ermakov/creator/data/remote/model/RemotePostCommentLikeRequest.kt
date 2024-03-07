package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemotePostCommentLikeRequest(
    @SerializedName("postCommentId")
    val postCommentId: Long,
    @SerializedName("userId")
    val userId: String
)