package ru.ermakov.creator.di

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.domain.repository.UserRepository
import ru.ermakov.creator.domain.useCase.account.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.passwordRecovery.PasswordRecoveryUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignInUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignedInUseCase
import ru.ermakov.creator.domain.useCase.signUp.SignUpUseCase
import ru.ermakov.creator.presentation.exception.ExceptionHandler
import ru.ermakov.creator.presentation.screen.account.AccountViewModelFactory
import ru.ermakov.creator.presentation.screen.editProfile.EditProfileViewModelFactory
import ru.ermakov.creator.presentation.screen.passwordRecovery.PasswordRecoveryViewModelFactory
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
        passwordRecoveryUseCase: PasswordRecoveryUseCase,
        exceptionHandler: ExceptionHandler
    ): PasswordRecoveryViewModelFactory {
        return PasswordRecoveryViewModelFactory(
            passwordRecoveryUseCase = passwordRecoveryUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideAccountViewModelFactory(
        exceptionHandler: ExceptionHandler,
        getCurrentUserUseCase: GetCurrentUserUseCase
    ): AccountViewModelFactory {
        return AccountViewModelFactory(
            exceptionHandler = exceptionHandler,
            getCurrentUserUseCase = getCurrentUserUseCase
        )
    }

    @Provides
    fun provideEditProfileViewModelFactory(
        exceptionHandler: ExceptionHandler,
        getCurrentUserUseCase: GetCurrentUserUseCase
    ): EditProfileViewModelFactory {
        return EditProfileViewModelFactory(
            exceptionHandler = exceptionHandler,
            getCurrentUserUseCase = getCurrentUserUseCase
        )
    }
}