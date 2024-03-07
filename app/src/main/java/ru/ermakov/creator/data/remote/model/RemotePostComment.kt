package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemotePostComment(
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
    @SerializedName("likeCount")
    val likeCount: Long,
    @SerializedName("isLiked")
    val isLiked: Boolean,
    @SerializedName("isEdited")
    val isEdited: Boolean
)