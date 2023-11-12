package ru.ermakov.creator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ermakov.creator.data.local.dao.UserDao
import ru.ermakov.creator.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CreatorDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}