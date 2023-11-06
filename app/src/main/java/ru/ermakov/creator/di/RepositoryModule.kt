package ru.ermakov.creator.di

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.dataSource.local.AuthLocalDataSource
import ru.ermakov.creator.data.exception.ExceptionHandler
import ru.ermakov.creator.data.repository.user.auth.AuthRepositoryImpl
import ru.ermakov.creator.data.dataSource.local.UserLocalDataSource
import ru.ermakov.creator.data.dataSource.remote.UserRemoteDataSource
import ru.ermakov.creator.data.repository.user.UserRepositoryImpl
import ru.ermakov.creator.data.service.AuthService
import ru.ermakov.creator.data.service.LocalStorage
import ru.ermakov.creator.data.service.RemoteStorage
import ru.ermakov.creator.data.repository.user.auth.AuthRepository
import ru.ermakov.creator.data.repository.user.UserRepository

@Module
class RepositoryModule {
    @Provides
    fun provideAuthRepository(
        userRepository: UserRepository,
        authLocalDataSource: AuthLocalDataSource,
        authService: AuthService,
        exceptionHandler: ExceptionHandler
    ): AuthRepository {
        return AuthRepositoryImpl(
            userRepository = userRepository,
            authLocalDataSource = authLocalDataSource,
            authService = authService,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideUserRepository(
        userLocalDataSource: UserLocalDataSource,
        userRemoteDataSource: UserRemoteDataSource,
        localStorage: LocalStorage,
        remoteStorage: RemoteStorage,
        authService: AuthService
    ): UserRepository {
        return UserRepositoryImpl(
            userLocalDataSource = userLocalDataSource,
            userRemoteDataSource = userRemoteDataSource,
            localStorage = localStorage,
            remoteStorage = remoteStorage,
            authService = authService
        )
    }
}