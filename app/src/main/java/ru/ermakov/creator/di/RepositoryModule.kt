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
import ru.ermakov.creator.data.remote.dataSource.CreditGoalRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.FileRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.FollowRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.PostCommentRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.PostRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.SubscriptionRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.TagRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.TransactionRemoteDataSource
import ru.ermakov.creator.data.remote.dataSource.UserSubscriptionRemoteDataSource
import ru.ermakov.creator.data.repository.CategoryRepositoryImpl
import ru.ermakov.creator.data.repository.CreatorRepositoryImpl
import ru.ermakov.creator.data.repository.CreditGoalRepositoryImpl
import ru.ermakov.creator.data.repository.FileRepositoryImpl
import ru.ermakov.creator.data.repository.FollowRepositoryImpl
import ru.ermakov.creator.data.repository.PostCommentRepositoryImpl
import ru.ermakov.creator.data.repository.PostRepositoryImpl
import ru.ermakov.creator.data.repository.SubscriptionRepositoryImpl
import ru.ermakov.creator.data.repository.TagRepositoryImpl
import ru.ermakov.creator.data.repository.TransactionRepositoryImpl
import ru.ermakov.creator.data.repository.UserSubscriptionRepositoryImpl
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.domain.repository.CategoryRepository
import ru.ermakov.creator.domain.repository.CreatorRepository
import ru.ermakov.creator.domain.repository.CreditGoalRepository
import ru.ermakov.creator.domain.repository.FileRepository
import ru.ermakov.creator.domain.repository.FollowRepository
import ru.ermakov.creator.domain.repository.PostCommentRepository
import ru.ermakov.creator.domain.repository.PostRepository
import ru.ermakov.creator.domain.repository.SubscriptionRepository
import ru.ermakov.creator.domain.repository.TagRepository
import ru.ermakov.creator.domain.repository.TransactionRepository
import ru.ermakov.creator.domain.repository.UserRepository
import ru.ermakov.creator.domain.repository.UserSubscriptionRepository

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
        return FollowRepositoryImpl(followRemoteDataSource = followRemoteDataSource)
    }

    @Provides
    fun provideSubscriptionRepository(
        subscriptionRemoteDataSource: SubscriptionRemoteDataSource,
    ): SubscriptionRepository {
        return SubscriptionRepositoryImpl(
            subscriptionRemoteDataSource = subscriptionRemoteDataSource
        )
    }

    @Provides
    fun provideUserSubscriptionRepository(
        userSubscriptionRemoteDataSource: UserSubscriptionRemoteDataSource,
    ): UserSubscriptionRepository {
        return UserSubscriptionRepositoryImpl(
            userSubscriptionRemoteDataSource = userSubscriptionRemoteDataSource
        )
    }

    @Provides
    fun provideTransactionRepository(
        transactionRemoteDataSource: TransactionRemoteDataSource
    ): TransactionRepository {
        return TransactionRepositoryImpl(transactionRemoteDataSource = transactionRemoteDataSource)
    }

    @Provides
    fun provideCreditGoalRepository(
        creditGoalRemoteDataSource: CreditGoalRemoteDataSource
    ): CreditGoalRepository {
        return CreditGoalRepositoryImpl(creditGoalRemoteDataSource = creditGoalRemoteDataSource)
    }

    @Provides
    fun providePostRepository(postRemoteDataSource: PostRemoteDataSource): PostRepository {
        return PostRepositoryImpl(postRemoteDataSource = postRemoteDataSource)
    }

    @Provides
    fun provideTagRepository(tagRemoteDataSource: TagRemoteDataSource): TagRepository {
        return TagRepositoryImpl(tagRemoteDataSource = tagRemoteDataSource)
    }

    @Provides
    fun providePostCommentRepository(postCommentRemoteDataSource: PostCommentRemoteDataSource): PostCommentRepository {
        return PostCommentRepositoryImpl(
            postCommentRemoteDataSource = postCommentRemoteDataSource
        )
    }
}