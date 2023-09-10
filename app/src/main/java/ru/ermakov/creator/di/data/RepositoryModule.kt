package ru.ermakov.creator.di.data

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.repository.AuthRepositoryImpl
import ru.ermakov.creator.data.repository.mapper.AuthUserToRemoteUserMapper
import ru.ermakov.creator.data.service.AuthService
import ru.ermakov.creator.data.storage.local.SingInDataSource
import ru.ermakov.creator.data.storage.remote.UserRemoteDataSource
import ru.ermakov.creator.domain.repository.AuthRepository

@Module
class RepositoryModule {
    @Provides
    fun provideAuthRepository(
        authService: AuthService,
        userRemoteDataSource: UserRemoteDataSource,
        singInDataSource: SingInDataSource,
        authUserToRemoteUserMapper: AuthUserToRemoteUserMapper
    ): AuthRepository {
        return AuthRepositoryImpl(
            authService = authService,
            userRemoteDataSource = userRemoteDataSource,
            singInDataSource = singInDataSource,
            authUserToRemoteUserMapper = authUserToRemoteUserMapper
        )
    }
}