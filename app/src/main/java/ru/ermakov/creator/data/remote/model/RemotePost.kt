package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemotePost(
    @SerializedName("id")
    val id: Long,
    @SerializedName("creator")
    val remoteCreator: RemoteCreator,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("images")
    val remoteImages: List<RemoteImage>,
    @SerializedName("tags")
    val remoteTags: List<RemoteTag>,
    @SerializedName("requiredSubscriptions")
    val remoteRequiredSubscriptions: List<RemoteSubscription>,
    @SerializedName("likeCount")
    val likeCount: Long,
    @SerializedName("commentCount")
    val commentCount: Long,
    @SerializedName("publicationDate")
    val publicationDate: String,
    @SerializedName("isAvailable")
    val isAvailable: Boolean,
    @SerializedName("isLiked")
    val isLiked: Boolean,
    @SerializedName("isEdited")
    val isEdited: Boolean,
)
