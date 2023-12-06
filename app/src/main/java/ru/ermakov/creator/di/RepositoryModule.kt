package ru.ermakov.creator.di

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.local.dataSource.AuthLocalDataSource
import ru.ermakov.creator.data.local.dataSource.FileLocalDataSource
import ru.ermakov.creator.data.repository.AuthRepositoryImpl
import ru.ermakov.creator.data.remote.dataSource.UserRemoteDataSource
import ru.ermakov.creator.data.repository.UserRepositoryImpl
import ru.ermakov.creator.data.remote.dataSource.AuthRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.CategoryRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.CreatorRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.FileRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.FollowRemoteDataSource
import ru.ermakov.creator.data.repository.CategoryRepositoryImpl
import ru.ermakov.creator.data.repository.CreatorRepositoryImpl
import ru.ermakov.creator.data.repository.FileRepositoryImpl
import ru.ermakov.creator.data.repository.FollowRepositoryImpl
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.domain.repository.CategoryRepository
import ru.ermakov.creator.domain.repository.CreatorRepository
import ru.ermakov.creator.domain.repository.FileRepository
import ru.ermakov.creator.domain.repository.FollowRepository
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
    fun provideUserRepository(userRemoteDataSource: UserRemoteDataSource): UserRepository {
        return UserRepositoryImpl(
            userRemoteDataSource = userRemoteDataSource,
        )
    }

    @Provides
    fun provideCreatorRepository(creatorRemoteDataSource: CreatorRemoteDataSource): CreatorRepository {
        return CreatorRepositoryImpl(creatorRemoteDataSource = creatorRemoteDataSource)
    }

    @Provides
    fun provideCategoryRepository(
        categoryRemoteDataSource: CategoryRemoteDataSource,
    ): CategoryRepository {
        return CategoryRepositoryImpl(
            categoryRemoteDataSource = categoryRemoteDataSource
        )
    }

    @Provides
    fun provideFileRepository(
        fileLocalDataSource: FileLocalDataSource,
        fileRemoteDataSource: FileRemoteDataSource
    ): FileRepository {
        return FileRepositoryImpl(
            fileLocalDataSource = fileLocalDataSource,
            fileRemoteDataSource = fileRemoteDataSource
        )
    }

    @Provides
    fun provideFollowRepository(
        followRemoteDataSource: FollowRemoteDataSource,
    ): FollowRepository {
        return FollowRepositoryImpl(
            followRemoteDataSource = followRemoteDataSource,
        )
    }
}