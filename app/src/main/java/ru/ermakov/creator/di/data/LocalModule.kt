package ru.ermakov.creator.di.data

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.dataSource.local.SingInLocalDataSource
import ru.ermakov.creator.data.dataSource.local.UserLocalDataSourceImpl
import ru.ermakov.creator.data.dataSource.local.database.CreatorDatabase
import ru.ermakov.creator.data.dataSource.local.database.dao.UserDao
import ru.ermakov.creator.data.repository.user.UserLocalDataSource
import ru.ermakov.creator.data.service.LocalStorage
import ru.ermakov.creator.util.Constant.Companion.PROJECT_NAME
import java.io.File
import javax.inject.Singleton

@Module
class LocalModule {
    @Provides
    fun provideExternalStorageFilesDir(context: Context): File {
        return context.getExternalFilesDir(null)!!
    }

    @Provides
    fun provideLocalStorage(externalStorageFilesDir: File): LocalStorage {
        return LocalStorage(externalStorageFilesDir = externalStorageFilesDir)
    }

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PROJECT_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideSignInDataSource(sharedPreferences: SharedPreferences): SingInLocalDataSource {
        return SingInLocalDataSource(sharedPreferences = sharedPreferences)
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