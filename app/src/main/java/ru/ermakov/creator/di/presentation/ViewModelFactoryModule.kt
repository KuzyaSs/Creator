package ru.ermakov.creator.di.presentation

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.domain.useCase.auth.passwordRecovery.PasswordRecoveryUseCase
import ru.ermakov.creator.domain.useCase.auth.signIn.SignInUseCase
import ru.ermakov.creator.domain.useCase.auth.signIn.SignedInUseCase
import ru.ermakov.creator.domain.useCase.auth.signUp.SignUpUseCase
import ru.ermakov.creator.presentation.viewModel.auth.passwordRecovery.PasswordRecoveryViewModelFactory
import ru.ermakov.creator.presentation.viewModel.auth.signIn.SignInViewModelFactory
import ru.ermakov.creator.presentation.viewModel.auth.signUp.SignUpViewModelFactory
import ru.ermakov.creator.presentation.viewModel.auth.splash.SplashViewModelFactory

@Module
class ViewModelFactoryModule {
    @Provides
    fun provideSplashViewModelFactory(signedInUseCase: SignedInUseCase): SplashViewModelFactory {
        return SplashViewModelFactory(signedInUseCase = signedInUseCase)
    }

    @Provides
    fun provideSignInViewModelFactory(signInUseCase: SignInUseCase): SignInViewModelFactory {
        return SignInViewModelFactory(signInUseCase = signInUseCase)
    }

    @Provides
    fun provideSignUpViewModelFactory(signUpUseCase: SignUpUseCase): SignUpViewModelFactory {
        return SignUpViewModelFactory(signUpUseCase = signUpUseCase)
    }

    @Provides
    fun providePasswordRecoveryViewModelFactory(
        passwordRecoveryUseCase: PasswordRecoveryUseCase
    ): PasswordRecoveryViewModelFactory {
        return PasswordRecoveryViewModelFactory(passwordRecoveryUseCase = passwordRecoveryUseCase)
    }
}