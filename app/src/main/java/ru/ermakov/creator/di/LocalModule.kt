package ru.ermakov.creator.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.local.CreatorDatabase
import ru.ermakov.creator.data.local.dao.UserDao
import ru.ermakov.creator.data.local.dataSource.AuthLocalDataSource
import ru.ermakov.creator.data.local.dataSource.AuthLocalDataSourceImpl
import ru.ermakov.creator.data.local.dataSource.FileLocalDataSource
import ru.ermakov.creator.data.local.dataSource.FileLocalDataSourceImpl
import ru.ermakov.creator.data.local.dataSource.UserLocalDataSource
import ru.ermakov.creator.data.local.dataSource.UserLocalDataSourceImpl
import java.io.File
import javax.inject.Singleton

private const val PROJECT_NAME = "ru.ermakov.creator"

@Module
class LocalModule {
    @Provides
    fun provideExternalStorageFilesDir(context: Context): File {
        return context.getExternalFilesDir(null)!!
    }

    @Provides
    fun provideFileLocalDataSource(externalStorageFilesDir: File): FileLocalDataSource {
        return FileLocalDataSourceImpl(externalStorageFilesDir = externalStorageFilesDir)
    }

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PROJECT_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideAuthLocalDataSource(sharedPreferences: SharedPreferences): AuthLocalDataSource {
        return AuthLocalDataSourceImpl(sharedPreferences = sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideCreatorDatabase(context: Context): CreatorDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = CreatorDatabase::class.java,
            name = PROJECT_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserDao(creatorDatabase: CreatorDatabase): UserDao {
        return creatorDatabase.getUserDao()
    }

    @Provides
    fun provideUserLocalDataSource(userDao: UserDao): UserLocalDataSource {
        return UserLocalDataSourceImpl(userDao = userDao)
    }
}