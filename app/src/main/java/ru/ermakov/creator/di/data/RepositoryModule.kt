package ru.ermakov.creator.di.data

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.dataSource.local.SingInLocalDataSource
import ru.ermakov.creator.data.repository.auth.AuthRepositoryImpl
import ru.ermakov.creator.data.repository.user.UserLocalDataSource
import ru.ermakov.creator.data.repository.user.UserRemoteDataSource
import ru.ermakov.creator.data.repository.user.UserRepositoryImpl
import ru.ermakov.creator.data.service.AuthService
import ru.ermakov.creator.data.service.LocalStorage
import ru.ermakov.creator.data.service.RemoteStorage
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.domain.repository.UserRepository

@Module
class RepositoryModule {
    @Provides
    fun provideAuthRepository(
        userRepository: UserRepository,
        singInLocalDataSource: SingInLocalDataSource,
        authService: AuthService
    ): AuthRepository {
        return AuthRepositoryImpl(
            userRepository = userRepository,
            singInLocalDataSource = singInLocalDataSource,
            authService = authService
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