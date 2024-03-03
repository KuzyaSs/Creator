package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteComment(
    @SerializedName("id")
    val id: Long,
    @SerializedName("user")
    val remoteUser: RemoteUser,
    @SerializedName("postId")
    val postId: Long,
    @SerializedName("replyCommentId")
    val replyCommentId: Long,
    @SerializedName("content")
    val content: String,
    @SerializedName("publicationDate")
    val publicationDate: String,
    @SerializedName("isEdited")
    val isEdited: Boolean
)