package ru.ermakov.creator.di.data

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.storage.local.SingInDataSource
import ru.ermakov.creator.data.storage.local.database.CreatorDatabase
import ru.ermakov.creator.data.storage.local.database.dao.UserDao
import ru.ermakov.creator.util.Constant.Companion.PROJECT_NAME
import javax.inject.Singleton

@Module
class LocalModule {
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
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PROJECT_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideSignInDataSource(sharedPreferences: SharedPreferences): SingInDataSource {
        return SingInDataSource(sharedPreferences = sharedPreferences)
    }
}