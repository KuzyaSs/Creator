package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName
import ru.ermakov.creator.domain.model.Category

data class RemoteCreator(
    @SerializedName("user")
    val remoteUser: RemoteUser,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("followerCount")
    val followerCount: Long,
    @SerializedName("subscriberCount")
    val subscriberCount: Long,
    @SerializedName("postCount")
    val postCount: Long
)