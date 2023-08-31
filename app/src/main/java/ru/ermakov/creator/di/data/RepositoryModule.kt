package ru.ermakov.creator.di.data

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.repository.AuthRepositoryImpl
import ru.ermakov.creator.data.repository.mapper.AuthUserToRemoteUserMapper
import ru.ermakov.creator.data.service.auth.AuthService
import ru.ermakov.creator.data.storage.local.UserDataSource
import ru.ermakov.creator.data.storage.remote.UserRemoteDataSource
import ru.ermakov.creator.domain.repository.AuthRepository

@Module
class RepositoryModule {
    @Provides
    fun provideAuthRepository(
        authService: AuthService,
        userRemoteDataSource: UserRemoteDataSource,
        userDataSource: UserDataSource,
        authUserToRemoteUserMapper: AuthUserToRemoteUserMapper
    ): AuthRepository {
        return AuthRepositoryImpl(
            authService = authService,
            userRemoteDataSource = userRemoteDataSource,
            userDataSource = userDataSource,
            authUserToRemoteUserMapper = authUserToRemoteUserMapper
        )
    }
}