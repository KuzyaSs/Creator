package ru.ermakov.creator.di.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.repository.AuthRepositoryImpl
import ru.ermakov.creator.data.repository.mapper.AuthUserToDomainUserMapper
import ru.ermakov.creator.data.repository.mapper.AuthUserToUserRemoteEntityMapper
import ru.ermakov.creator.domain.repository.AuthRepository

@Module
class RepositoryModule {
    @Provides
    fun provideAuthRepository(
        authService: FirebaseAuth,
        databaseService: FirebaseFirestore,
        authUserToUserRemoteEntityMapper: AuthUserToUserRemoteEntityMapper,
        authUserToDomainUserMapper: AuthUserToDomainUserMapper
    ): AuthRepository {
        return AuthRepositoryImpl(
            authService = authService,
            databaseService = databaseService,
            authUserToUserRemoteEntityMapper = authUserToUserRemoteEntityMapper,
            authUserToDomainUserMapper = authUserToDomainUserMapper
        )
    }
}