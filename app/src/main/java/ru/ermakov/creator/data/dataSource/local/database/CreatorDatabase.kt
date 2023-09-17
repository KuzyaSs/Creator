package ru.ermakov.creator.data.dataSource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ermakov.creator.data.dataSource.local.database.dao.UserDao
import ru.ermakov.creator.data.dataSource.local.database.model.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CreatorDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}