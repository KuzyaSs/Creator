package ru.ermakov.creator.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.exception.ApiExceptionLocalizer
import ru.ermakov.creator.data.exception.ApiExceptionLocalizerImpl
import ru.ermakov.creator.presentation.util.ExceptionHandler
import ru.ermakov.creator.presentation.util.ExceptionHandlerImpl
import ru.ermakov.creator.presentation.util.TextLocalizer
import ru.ermakov.creator.presentation.util.TextLocalizerImpl

@Module
class ExceptionModule {
    @Provides
    fun provideExceptionHandler(): ExceptionHandler {
        return ExceptionHandlerImpl()
    }

    @Provides
    fun provideExceptionLocalizer(context: Context): TextLocalizer {
        return TextLocalizerImpl(context = context)
    }

    @Provides
    fun provideApiExceptionLocalizer(gson: Gson): ApiExceptionLocalizer {
        return ApiExceptionLocalizerImpl(gson = gson)
    }
}