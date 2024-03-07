package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemotePostCommentRequest (
    @SerializedName("id")
    val userId: String,
    @SerializedName("postId")
    val postId: Long,
    @SerializedName("replyCommentId")
    val replyCommentId: Long?,
    @SerializedName("content")
    val content: String
)