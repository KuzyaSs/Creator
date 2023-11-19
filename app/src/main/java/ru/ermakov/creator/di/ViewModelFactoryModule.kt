package ru.ermakov.creator.di

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.account.SignOutUseCase
import ru.ermakov.creator.domain.useCase.common.CancelUploadTaskUseCase
import ru.ermakov.creator.domain.useCase.common.UpdateUserUseCase
import ru.ermakov.creator.domain.useCase.common.UploadFileUseCase
import ru.ermakov.creator.domain.useCase.passwordRecovery.RecoverPasswordByEmailUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignInUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignedInUseCase
import ru.ermakov.creator.domain.useCase.signUp.SignUpUseCase
import ru.ermakov.creator.presentation.exception.ExceptionHandler
import ru.ermakov.creator.presentation.screen.editProfile.EditProfileViewModelFactory
import ru.ermakov.creator.presentation.screen.following.FollowingViewModelFactory
import ru.ermakov.creator.presentation.screen.passwordRecovery.PasswordRecoveryViewModelFactory
import ru.ermakov.creator.presentation.screen.search.SearchViewModelFactory
import ru.ermakov.creator.presentation.screen.signIn.SignInViewModelFactory
import ru.ermakov.creator.presentation.screen.signUp.SignUpViewModelFactory
import ru.ermakov.creator.presentation.screen.splash.SplashViewModelFactory

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
    fun provideSearchViewModelFactory(exceptionHandler: ExceptionHandler): SearchViewModelFactory {
        return SearchViewModelFactory(exceptionHandler = exceptionHandler)
    }

    @Provides
    fun provideEditProfileViewModelFactory(
        getCurrentUserUseCase: GetCurrentUserUseCase,
        updateUserUseCase: UpdateUserUseCase,
        uploadFileUseCase: UploadFileUseCase,
        cancelUploadTaskUseCase: CancelUploadTaskUseCase,
        exceptionHandler: ExceptionHandler
    ): EditProfileViewModelFactory {
        return EditProfileViewModelFactory(
            getCurrentUserUseCase = getCurrentUserUseCase,
            updateUserUseCase = updateUserUseCase,
            uploadFileUseCase = uploadFileUseCase,
            cancelUploadTaskUseCase = cancelUploadTaskUseCase,
            exceptionHandler = exceptionHandler
        )
    }
}