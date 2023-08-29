package ru.ermakov.creator.di.data

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.repository.mapper.AuthUserToDomainUserMapper
import ru.ermakov.creator.data.repository.mapper.AuthUserToUserRemoteEntityMapper

@Module
class MapperModule {
    @Provides
    fun provideAuthUserToUserRemoteEntityMapper(): AuthUserToUserRemoteEntityMapper {
        return AuthUserToUserRemoteEntityMapper()
    }

    @Provides
    fun provideAuthUserToDomainUserMapper(): AuthUserToDomainUserMapper {
        return AuthUserToDomainUserMapper()
    }
}