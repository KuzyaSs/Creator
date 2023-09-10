package ru.ermakov.creator.data.storage.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val email: String,
    val username: String,
    val image: String,
    @ColumnInfo(name = "background_image")
    val backgroundImage: String,
    val about: String
)