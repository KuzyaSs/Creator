package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteUser(
    @SerializedName("id")
    val id: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("bio")
    val bio: String,
    @SerializedName("profileAvatarUrl")
    val profileAvatarUrl: String,
    @SerializedName("profileBackgroundUrl")
    val profileBackgroundUrl: String,
    @SerializedName("isModerator")
    val isModerator: Boolean,
    @SerializedName("registrationDate")
    val registrationDate: String
)