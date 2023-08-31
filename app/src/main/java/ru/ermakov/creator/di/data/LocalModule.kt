package ru.ermakov.creator.di.data

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.storage.local.UserDataSource
import ru.ermakov.creator.util.Constant.Companion.SHARED_PREFS_NAME

@Module
class LocalModule {
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideSignInDataSource(sharedPreferences: SharedPreferences): UserDataSource {
        return UserDataSource(sharedPreferences = sharedPreferences)
    }
}