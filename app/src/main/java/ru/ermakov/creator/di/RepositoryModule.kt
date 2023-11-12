package ru.ermakov.creator.di

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.exception.ExceptionHandler
import ru.ermakov.creator.data.local.dataSource.AuthLocalDataSource
import ru.ermakov.creator.data.local.dataSource.FileLocalDataSource
import ru.ermakov.creator.data.repository.user.auth.AuthRepositoryImpl
import ru.ermakov.creator.data.local.dataSource.UserLocalDataSource
import ru.ermakov.creator.data.remote.dataSource.UserRemoteDataSource
import ru.ermakov.creator.data.repository.user.UserRepositoryImpl
import ru.ermakov.creator.data.remote.dataSource.AuthRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.FileRemoteDataSource
import ru.ermakov.creator.data.repository.user.auth.AuthRepository
import ru.ermakov.creator.data.repository.user.UserRepository

@Module
class RepositoryModule {
    @Provides
    fun provideAuthRepository(
        userRepository: UserRepository,
        authLocalDataSource: AuthLocalDataSource,
        authRemoteDataSource: AuthRemoteDataSource,
        exceptionHandler: ExceptionHandler
    ): AuthRepository {
        return AuthRepositoryImpl(
            userRepository = userRepository,
            authLocalDataSource = authLocalDataSource,
            authRemoteDataSource = authRemoteDataSource,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideUserRepository(
        userLocalDataSource: UserLocalDataSource,
        userRemoteDataSource: UserRemoteDataSource,
        fileLocalDataSource: FileLocalDataSource,
        fileRemoteDataSource: FileRemoteDataSource,
        authRemoteDataSource: AuthRemoteDataSource
    ): UserRepository {
        return UserRepositoryImpl(
            userLocalDataSource = userLocalDataSource,
            userRemoteDataSource = userRemoteDataSource,
            fileLocalDataSource = fileLocalDataSource,
            fileRemoteDataSource = fileRemoteDataSource,
            authRemoteDataSource = authRemoteDataSource
        )
    }
}