package ru.ermakov.creator.di.data

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.repository.mapper.AuthUserToRemoteUserMapper

@Module
class MapperModule {
    @Provides
    fun provideAuthUserToRemoteUserMapper(): AuthUserToRemoteUserMapper {
        return AuthUserToRemoteUserMapper()
    }
}