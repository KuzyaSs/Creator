package ru.ermakov.creator.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.exception.ExceptionHandler
import ru.ermakov.creator.data.exception.ExceptionHandlerImpl
import ru.ermakov.creator.presentation.exception.ExceptionLocalizer
import ru.ermakov.creator.presentation.exception.ExceptionLocalizerImpl
import javax.inject.Singleton

@Module
class ExceptionModule {
    @Provides
    fun provideExceptionHandler(): ExceptionHandler {
        return ExceptionHandlerImpl()
    }

    @Provides
    fun provideExceptionLocalizer(context: Context): ExceptionLocalizer {
        return ExceptionLocalizerImpl(context = context)
    }
}