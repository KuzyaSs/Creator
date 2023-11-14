package ru.ermakov.creator.data.remote.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class RemoteUser(
    @SerializedName("id")
    val id: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("about")
    val about: String,
    @SerializedName("profileAvatarUrl")
    val profileAvatarUrl: String,
    @SerializedName("profileBackgroundUrl")
    val profileBackgroundUrl: String,
    @SerializedName("registrationDate")
    val registrationDate: LocalDate
)