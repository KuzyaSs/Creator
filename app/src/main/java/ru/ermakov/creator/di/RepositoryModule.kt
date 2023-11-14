package ru.ermakov.creator.di

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.presentation.exception.ExceptionHandler
import ru.ermakov.creator.data.local.dataSource.AuthLocalDataSource
import ru.ermakov.creator.data.local.dataSource.FileLocalDataSource
import ru.ermakov.creator.data.repository.AuthRepositoryImpl
import ru.ermakov.creator.data.local.dataSource.UserLocalDataSource
import ru.ermakov.creator.data.remote.dataSource.UserRemoteDataSource
import ru.ermakov.creator.data.repository.UserRepositoryImpl
import ru.ermakov.creator.data.remote.dataSource.AuthRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.FileRemoteDataSource
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.domain.repository.UserRepository

@Module
class RepositoryModule {
    @Provides
    fun provideAuthRepository(
        authLocalDataSource: AuthLocalDataSource,
        authRemoteDataSource: AuthRemoteDataSource,
    ): AuthRepository {
        return AuthRepositoryImpl(
            authLocalDataSource = authLocalDataSource,
            authRemoteDataSource = authRemoteDataSource,
        )
    }

    @Provides
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource,
    ): UserRepository {
        return UserRepositoryImpl(
            userRemoteDataSource = userRemoteDataSource,
        )
    }
}