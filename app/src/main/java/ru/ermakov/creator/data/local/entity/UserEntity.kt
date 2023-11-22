package ru.ermakov.creator.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val username: String,
    val email: String,
    val bio: String,
    @ColumnInfo(name = "profile_avatar_url")
    val profileAvatarUrl: String,
    @ColumnInfo(name = "profile_background_url")
    val profileBackgroundUrl: String,
    @ColumnInfo(name = "registration_date")
    val registrationDate: Long
)