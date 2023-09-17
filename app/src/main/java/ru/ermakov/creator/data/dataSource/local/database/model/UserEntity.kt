package ru.ermakov.creator.data.dataSource.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val email: String,
    val username: String,
    @ColumnInfo(name = "profile_avatar")
    val profileAvatar: String,
    @ColumnInfo(name = "profile_background")
    val profileBackground: String,
    val about: String
)