package ru.ermakov.creator.di

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.domain.useCase.account.SignOutUseCase
import ru.ermakov.creator.domain.useCase.blog.FollowUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCreatorByUserIdUseCase
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
import ru.ermakov.creator.domain.useCase.editProfile.UpdateBioUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateUserImageUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateUsernameUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UploadProfileFileUseCase
import ru.ermakov.creator.domain.useCase.editSubscription.EditSubscriptionUseCase
import ru.ermakov.creator.domain.useCase.editSubscription.GetSubscriptionByIdUseCase
import ru.ermakov.creator.domain.useCase.passwordRecovery.RecoverPasswordByEmailUseCase
import ru.ermakov.creator.domain.useCase.search.SearchCreatorsUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignInUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignedInUseCase
import ru.ermakov.creator.domain.useCase.signUp.SignUpUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.GetSubscriptionsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.GetUserSubscriptionsByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.UnsubscribeUseCase
import ru.ermakov.creator.presentation.screen.blog.BlogViewModelFactory
import ru.ermakov.creator.presentation.screen.changePassword.ChangePasswordViewModelFactory
import ru.ermakov.creator.presentation.screen.chooseCategory.ChooseCategoryViewModelFactory
import ru.ermakov.creator.presentation.screen.createSubscription.CreateSubscriptionViewModelFactory
import ru.ermakov.creator.presentation.screen.editProfile.EditProfileViewModelFactory
import ru.ermakov.creator.presentation.screen.editSubscription.EditSubscriptionViewModelFactory
import ru.ermakov.creator.presentation.screen.following.FollowingViewModelFactory
import ru.ermakov.creator.presentation.screen.passwordRecovery.PasswordRecoveryViewModelFactory
import ru.ermakov.creator.presentation.screen.search.searchCreator.SearchCreatorViewModelFactory
import ru.ermakov.creator.presentation.screen.search.searchPost.SearchPostViewModelFactory
import ru.ermakov.creator.presentation.screen.signIn.SignInViewModelFactory
import ru.ermakov.creator.presentation.screen.signUp.SignUpViewModelFactory
import ru.ermakov.creator.presentation.screen.splash.SplashViewModelFactory
import ru.ermakov.creator.presentation.screen.subscriptions.SubscriptionsViewModelFactory
import ru.ermakov.creator.presentation.util.ExceptionHandler

@Module
class ViewModelFactoryModule {
    @Provides
    fun provideSplashViewModelFactory(
        signedInUseCase: SignedInUseCase,
        exceptionHandler: ExceptionHandler
    ): SplashViewModelFactory {
        return SplashViewModelFactory(
            signedInUseCase = signedInUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideSignInViewModelFactory(
        signInUseCase: SignInUseCase,
        exceptionHandler: ExceptionHandler
    ): SignInViewModelFactory {
        return SignInViewModelFactory(
            signInUseCase = signInUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideSignUpViewModelFactory(
        signUpUseCase: SignUpUseCase,
        exceptionHandler: ExceptionHandler
    ): SignUpViewModelFactory {
        return SignUpViewModelFactory(
            signUpUseCase = signUpUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun providePasswordRecoveryViewModelFactory(
        recoverPasswordByEmailUseCase: RecoverPasswordByEmailUseCase,
        exceptionHandler: ExceptionHandler
    ): PasswordRecoveryViewModelFactory {
        return PasswordRecoveryViewModelFactory(
            recoverPasswordByEmailUseCase = recoverPasswordByEmailUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideFollowingViewModelFactory(
        getCurrentUserUseCase: GetCurrentUserUseCase,
        signOutUseCase: SignOutUseCase,
        exceptionHandler: ExceptionHandler
    ): FollowingViewModelFactory {
        return FollowingViewModelFactory(
            getCurrentUserUseCase = getCurrentUserUseCase,
            signOutUseCase = signOutUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideBlogViewModelFactory(
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        isFollowerByUserAndCreatorIdsUseCase: IsFollowerByUserAndCreatorIdsUseCase,
        followUseCase: FollowUseCase,
        unfollowUseCase: UnfollowUseCase,
        isSubscriberUseCase: IsSubscriberUseCase,
        getCreatorByUserIdUseCase: GetCreatorByUserIdUseCase,
        exceptionHandler: ExceptionHandler,
    ): BlogViewModelFactory {
        return BlogViewModelFactory(
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            isFollowerByUserAndCreatorIdsUseCase = isFollowerByUserAndCreatorIdsUseCase,
            followUseCase = followUseCase,
            unfollowUseCase = unfollowUseCase,
            isSubscriberUseCase = isSubscriberUseCase,
            getCreatorByUserIdUseCase = getCreatorByUserIdUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideSearchCreatorViewModelFactory(
        searchCreatorsUseCase: SearchCreatorsUseCase,
        exceptionHandler: ExceptionHandler
    ): SearchCreatorViewModelFactory {
        return SearchCreatorViewModelFactory(
            searchCreatorsUseCase = searchCreatorsUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideSearchPostViewModelFactory(exceptionHandler: ExceptionHandler): SearchPostViewModelFactory {
        return SearchPostViewModelFactory(exceptionHandler = exceptionHandler)
    }

    @Provides
    fun provideEditProfileViewModelFactory(
        getCurrentUserUseCase: GetCurrentUserUseCase,
        updateUserImageUseCase: UpdateUserImageUseCase,
        updateUsernameUseCase: UpdateUsernameUseCase,
        updateBioUseCase: UpdateBioUseCase,
        uploadProfileFileUseCase: UploadProfileFileUseCase,
        cancelUploadTaskUseCase: CancelUploadTaskUseCase,
        exceptionHandler: ExceptionHandler
    ): EditProfileViewModelFactory {
        return EditProfileViewModelFactory(
            getCurrentUserUseCase = getCurrentUserUseCase,
            updateUserImageUseCase = updateUserImageUseCase,
            updateUsernameUseCase = updateUsernameUseCase,
            updateBioUseCase = updateBioUseCase,
            uploadProfileFileUseCase = uploadProfileFileUseCase,
            cancelUploadTaskUseCase = cancelUploadTaskUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideChooseCategoryViewModelFactory(
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        getCategoriesByUserIdUseCase: GetCategoriesByUserIdUseCase,
        updateCategoriesUseCase: UpdateCategoriesUseCase,
        updateCategoryInListUseCase: UpdateCategoryInListUseCase,
        exceptionHandler: ExceptionHandler
    ): ChooseCategoryViewModelFactory {
        return ChooseCategoryViewModelFactory(
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            getCategoriesByUserIdUseCase = getCategoriesByUserIdUseCase,
            updateCategoriesUseCase = updateCategoriesUseCase,
            updateCategoryInListUseCase = updateCategoryInListUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideChangePasswordViewModelFactory(
        changePasswordUseCase: ChangePasswordUseCase,
        exceptionHandler: ExceptionHandler
    ): ChangePasswordViewModelFactory {
        return ChangePasswordViewModelFactory(
            changePasswordUseCase = changePasswordUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideSubscriptionsViewModelFactory(
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        getUserSubscriptionsByUserAndCreatorIdsUseCase: GetUserSubscriptionsByUserAndCreatorIdsUseCase,
        getSubscriptionsByCreatorIdUseCase: GetSubscriptionsByCreatorIdUseCase,
        unsubscribeUseCase: UnsubscribeUseCase,
        deleteSubscriptionByIdUseCase: DeleteSubscriptionByIdUseCase,
        exceptionHandler: ExceptionHandler
    ): SubscriptionsViewModelFactory {
        return SubscriptionsViewModelFactory(
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            getUserSubscriptionsByUserAndCreatorIdsUseCase = getUserSubscriptionsByUserAndCreatorIdsUseCase,
            getSubscriptionsByCreatorIdUseCase = getSubscriptionsByCreatorIdUseCase,
            unsubscribeUseCase = unsubscribeUseCase,
            deleteSubscriptionByIdUseCase = deleteSubscriptionByIdUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideCreateSubscriptionViewModelFactory(
        createSubscriptionUseCase: CreateSubscriptionUseCase,
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        exceptionHandler: ExceptionHandler
    ): CreateSubscriptionViewModelFactory {
        return CreateSubscriptionViewModelFactory(
            createSubscriptionUseCase = createSubscriptionUseCase,
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideEditSubscriptionViewModelFactory(
        getSubscriptionByIdUseCase: GetSubscriptionByIdUseCase,
        editSubscriptionUseCase: EditSubscriptionUseCase,
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        exceptionHandler: ExceptionHandler
    ): EditSubscriptionViewModelFactory {
        return EditSubscriptionViewModelFactory(
            getSubscriptionByIdUseCase = getSubscriptionByIdUseCase,
            editSubscriptionUseCase = editSubscriptionUseCase,
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            exceptionHandler = exceptionHandler
        )
    }
}