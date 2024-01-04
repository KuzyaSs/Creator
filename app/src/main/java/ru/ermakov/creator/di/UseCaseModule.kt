package ru.ermakov.creator.di

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.domain.repository.CategoryRepository
import ru.ermakov.creator.domain.repository.CreatorRepository
import ru.ermakov.creator.domain.repository.FileRepository
import ru.ermakov.creator.domain.repository.FollowRepository
import ru.ermakov.creator.domain.repository.SubscriptionRepository
import ru.ermakov.creator.domain.repository.UserRepository
import ru.ermakov.creator.domain.repository.UserSubscriptionRepository
import ru.ermakov.creator.domain.useCase.account.SignOutUseCase
import ru.ermakov.creator.domain.useCase.blog.FollowUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCreatorByUserIdUseCase
import ru.ermakov.creator.domain.useCase.blog.GetFollowByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.blog.IsFollowerByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.blog.IsSubscriberUseCase
import ru.ermakov.creator.domain.useCase.blog.UnfollowUseCase
import ru.ermakov.creator.domain.useCase.changePassword.ChangePasswordUseCase
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoriesUseCase
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoryInListUseCase
import ru.ermakov.creator.domain.useCase.createSubscription.CreateSubscriptionUseCase
import ru.ermakov.creator.domain.useCase.createSubscription.DeleteSubscriptionByIdUseCase
import ru.ermakov.creator.domain.useCase.shared.CancelUploadTaskUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCategoriesByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.shared.GetUserByIdUseCase
import ru.ermakov.creator.domain.useCase.shared.UpdateUserUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateBioUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateUserImageUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateUsernameUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UploadProfileFileUseCase
import ru.ermakov.creator.domain.useCase.editSubscription.EditSubscriptionUseCase
import ru.ermakov.creator.domain.useCase.editSubscription.GetSubscriptionByIdUseCase
import ru.ermakov.creator.domain.useCase.passwordRecovery.RecoverPasswordByEmailUseCase
import ru.ermakov.creator.domain.useCase.purchaseSubscription.PurchaseSubscriptionUseCase
import ru.ermakov.creator.domain.useCase.search.SearchCreatorsUseCase
import ru.ermakov.creator.domain.useCase.shared.GetBalanceByUserIdUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignInUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignedInUseCase
import ru.ermakov.creator.domain.useCase.signUp.SignUpUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.GetSubscriptionsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.GetUserSubscriptionsByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.UnsubscribeUseCase

@Module
class UseCaseModule {
    @Provides
    fun provideSignUpUseCase(
        authRepository: AuthRepository,
        userRepository: UserRepository
    ): SignUpUseCase {
        return SignUpUseCase(authRepository = authRepository, userRepository = userRepository)
    }

    @Provides
    fun provideSignInUseCase(authRepository: AuthRepository): SignInUseCase {
        return SignInUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideSignedInUseCase(authRepository: AuthRepository): SignedInUseCase {
        return SignedInUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideSignOutUseCase(authRepository: AuthRepository): SignOutUseCase {
        return SignOutUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideRecoverPasswordByEmailUseCase(authRepository: AuthRepository): RecoverPasswordByEmailUseCase {
        return RecoverPasswordByEmailUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideGetCurrentUserIdUseCase(authRepository: AuthRepository): GetCurrentUserIdUseCase {
        return GetCurrentUserIdUseCase(
            authRepository = authRepository,
        )
    }

    @Provides
    fun provideGetUserByIdUseCase(userRepository: UserRepository): GetUserByIdUseCase {
        return GetUserByIdUseCase(userRepository = userRepository)
    }

    @Provides
    fun provideGetCurrentUserUseCase(
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        userRepository: UserRepository
    ): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            userRepository = userRepository
        )
    }

    @Provides
    fun provideUpdateUserUseCase(userRepository: UserRepository): UpdateUserUseCase {
        return UpdateUserUseCase(userRepository = userRepository)
    }

    @Provides
    fun provideUpdateUserImageUseCase(
        getCurrentUserUseCase: GetCurrentUserUseCase,
        updateUserUseCase: UpdateUserUseCase
    ): UpdateUserImageUseCase {
        return UpdateUserImageUseCase(
            getCurrentUserUseCase = getCurrentUserUseCase,
            updateUserUseCase = updateUserUseCase
        )
    }

    @Provides
    fun provideUpdateUsernameUseCase(
        getCurrentUserUseCase: GetCurrentUserUseCase,
        updateUserUseCase: UpdateUserUseCase
    ): UpdateUsernameUseCase {
        return UpdateUsernameUseCase(
            getCurrentUserUseCase = getCurrentUserUseCase,
            updateUserUseCase = updateUserUseCase
        )
    }

    @Provides
    fun provideUpdateBioUseCase(
        getCurrentUserUseCase: GetCurrentUserUseCase,
        updateUserUseCase: UpdateUserUseCase
    ): UpdateBioUseCase {
        return UpdateBioUseCase(
            getCurrentUserUseCase = getCurrentUserUseCase,
            updateUserUseCase = updateUserUseCase
        )
    }

    @Provides
    fun provideUploadFileUseCase(fileRepository: FileRepository): UploadProfileFileUseCase {
        return UploadProfileFileUseCase(fileRepository = fileRepository)
    }

    @Provides
    fun provideCancelUploadTaskUseCase(fileRepository: FileRepository): CancelUploadTaskUseCase {
        return CancelUploadTaskUseCase(fileRepository = fileRepository)
    }

    @Provides
    fun provideGetCategoriesByUserIdUseCase(categoryRepository: CategoryRepository): GetCategoriesByUserIdUseCase {
        return GetCategoriesByUserIdUseCase(categoryRepository = categoryRepository)
    }

    @Provides
    fun provideUpdateCategoriesUseCase(categoryRepository: CategoryRepository): UpdateCategoriesUseCase {
        return UpdateCategoriesUseCase(categoryRepository = categoryRepository)
    }

    @Provides
    fun provideUpdateCategoryInListUseCase(): UpdateCategoryInListUseCase {
        return UpdateCategoryInListUseCase()
    }

    @Provides
    fun provideChangePasswordUseCase(authRepository: AuthRepository): ChangePasswordUseCase {
        return ChangePasswordUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideFollowUseCase(followRepository: FollowRepository): FollowUseCase {
        return FollowUseCase(followRepository = followRepository)
    }

    @Provides
    fun provideUnfollowUseCase(
        getFollowByUserAndCreatorIdsUseCase: GetFollowByUserAndCreatorIdsUseCase,
        followRepository: FollowRepository
    ): UnfollowUseCase {
        return UnfollowUseCase(
            getFollowByUserAndCreatorIdsUseCase = getFollowByUserAndCreatorIdsUseCase,
            followRepository = followRepository
        )
    }

    @Provides
    fun provideIsFollowerByUserAndCreatorIdsUseCase(followRepository: FollowRepository): IsFollowerByUserAndCreatorIdsUseCase {
        return IsFollowerByUserAndCreatorIdsUseCase(followRepository = followRepository)
    }

    @Provides
    fun provideGetFollowByUserAndCreatorIdsUseCase(followRepository: FollowRepository): GetFollowByUserAndCreatorIdsUseCase {
        return GetFollowByUserAndCreatorIdsUseCase(followRepository = followRepository)
    }

    @Provides
    fun provideIsSubscriberUseCase(): IsSubscriberUseCase {
        return IsSubscriberUseCase()
    }

    @Provides
    fun provideGetCreatorByUserIdUseCase(
        creatorRepository: CreatorRepository
    ): GetCreatorByUserIdUseCase {
        return GetCreatorByUserIdUseCase(creatorRepository = creatorRepository)
    }

    @Provides
    fun provideSearchCreatorsUseCase(creatorRepository: CreatorRepository): SearchCreatorsUseCase {
        return SearchCreatorsUseCase(creatorRepository = creatorRepository)
    }

    @Provides
    fun provideGetSubscriptionsByCreatorIdUseCase(
        subscriptionRepository: SubscriptionRepository
    ): GetSubscriptionsByCreatorIdUseCase {
        return GetSubscriptionsByCreatorIdUseCase(subscriptionRepository = subscriptionRepository)
    }

    @Provides
    fun provideCreateSubscriptionUseCase(subscriptionRepository: SubscriptionRepository): CreateSubscriptionUseCase {
        return CreateSubscriptionUseCase(subscriptionRepository = subscriptionRepository)
    }

    @Provides
    fun provideGetSubscriptionByIdUseCase(subscriptionRepository: SubscriptionRepository): GetSubscriptionByIdUseCase {
        return GetSubscriptionByIdUseCase(subscriptionRepository = subscriptionRepository)
    }

    @Provides
    fun provideEditSubscriptionUseCase(subscriptionRepository: SubscriptionRepository): EditSubscriptionUseCase {
        return EditSubscriptionUseCase(subscriptionRepository = subscriptionRepository)
    }

    @Provides
    fun provideGetUserSubscriptionsByUserAndCreatorIdsUseCase(
        userSubscriptionRepository: UserSubscriptionRepository
    ): GetUserSubscriptionsByUserAndCreatorIdsUseCase {
        return GetUserSubscriptionsByUserAndCreatorIdsUseCase(
            userSubscriptionRepository = userSubscriptionRepository
        )
    }

    @Provides
    fun provideUnsubscribeUseCase(
        userSubscriptionRepository: UserSubscriptionRepository
    ): UnsubscribeUseCase {
        return UnsubscribeUseCase(userSubscriptionRepository = userSubscriptionRepository)
    }

    @Provides
    fun provideDeleteSubscriptionByIdUseCase(
        subscriptionRepository: SubscriptionRepository
    ): DeleteSubscriptionByIdUseCase {
        return DeleteSubscriptionByIdUseCase(subscriptionRepository = subscriptionRepository)
    }

    @Provides
    fun providePurchaseSubscriptionUseCase(
        userSubscriptionRepository: UserSubscriptionRepository
    ): PurchaseSubscriptionUseCase {
        return PurchaseSubscriptionUseCase(userSubscriptionRepository = userSubscriptionRepository)
    }

    @Provides
    fun provideGetBalanceByUserIdUseCase(): GetBalanceByUserIdUseCase {
        return GetBalanceByUserIdUseCase()
    }
}