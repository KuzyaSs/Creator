package ru.ermakov.creator.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.ermakov.creator.presentation.exception.ExceptionHandler
import ru.ermakov.creator.presentation.exception.ExceptionHandlerImpl
import ru.ermakov.creator.presentation.exception.ExceptionLocalizer
import ru.ermakov.creator.presentation.exception.ExceptionLocalizerImpl

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