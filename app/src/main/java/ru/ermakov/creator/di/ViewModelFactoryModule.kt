package ru.ermakov.creator.di

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.domain.useCase.account.SignOutUseCase
import ru.ermakov.creator.domain.useCase.blog.FollowUseCase
import ru.ermakov.creator.domain.useCase.blog.GetCreatorByIdUseCase
import ru.ermakov.creator.domain.useCase.blog.IsFollowerByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.blog.IsSubscriberUseCase
import ru.ermakov.creator.domain.useCase.blog.UnfollowUseCase
import ru.ermakov.creator.domain.useCase.changePassword.ChangePasswordUseCase
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoriesUseCase
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoryInListUseCase
import ru.ermakov.creator.domain.useCase.common.CancelUploadTaskUseCase
import ru.ermakov.creator.domain.useCase.common.GetCategoriesByUserIdUseCase
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserIdUseCase
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateBioUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateUserImageUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateUsernameUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UploadProfileFileUseCase
import ru.ermakov.creator.domain.useCase.passwordRecovery.RecoverPasswordByEmailUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignInUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignedInUseCase
import ru.ermakov.creator.domain.useCase.signUp.SignUpUseCase
import ru.ermakov.creator.presentation.screen.blog.BlogViewModelFactory
import ru.ermakov.creator.presentation.screen.changePassword.ChangePasswordViewModelFactory
import ru.ermakov.creator.presentation.screen.chooseCategory.ChooseCategoryViewModelFactory
import ru.ermakov.creator.presentation.screen.editProfile.EditProfileViewModelFactory
import ru.ermakov.creator.presentation.screen.following.FollowingViewModelFactory
import ru.ermakov.creator.presentation.screen.passwordRecovery.PasswordRecoveryViewModelFactory
import ru.ermakov.creator.presentation.screen.search.SearchViewModelFactory
import ru.ermakov.creator.presentation.screen.signIn.SignInViewModelFactory
import ru.ermakov.creator.presentation.screen.signUp.SignUpViewModelFactory
import ru.ermakov.creator.presentation.screen.splash.SplashViewModelFactory
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
        getCreatorByIdUseCase: GetCreatorByIdUseCase,
        exceptionHandler: ExceptionHandler,
    ): BlogViewModelFactory {
        return BlogViewModelFactory(
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            isFollowerByUserAndCreatorIdsUseCase = isFollowerByUserAndCreatorIdsUseCase,
            followUseCase = followUseCase,
            unfollowUseCase = unfollowUseCase,
            isSubscriberUseCase = isSubscriberUseCase,
            getCreatorByIdUseCase = getCreatorByIdUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideSearchViewModelFactory(exceptionHandler: ExceptionHandler): SearchViewModelFactory {
        return SearchViewModelFactory(exceptionHandler = exceptionHandler)
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
}