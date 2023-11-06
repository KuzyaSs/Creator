package ru.ermakov.creator.di

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.repository.user.UserRepository
import ru.ermakov.creator.domain.useCase.passwordRecovery.PasswordRecoveryUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignInUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignedInUseCase
import ru.ermakov.creator.domain.useCase.signUp.SignUpUseCase
import ru.ermakov.creator.presentation.screen.account.AccountViewModelFactory
import ru.ermakov.creator.presentation.screen.editProfile.EditProfileViewModelFactory
import ru.ermakov.creator.presentation.screen.passwordRecovery.PasswordRecoveryViewModelFactory
import ru.ermakov.creator.presentation.screen.signIn.SignInViewModelFactory
import ru.ermakov.creator.presentation.screen.signUp.SignUpViewModelFactory
import ru.ermakov.creator.presentation.screen.splash.SplashViewModelFactory

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

    @Provides
    fun provideAccountViewModelFactory(userRepository: UserRepository): AccountViewModelFactory {
        return AccountViewModelFactory(userRepository = userRepository)
    }

    @Provides
    fun provideEditProfileViewModelFactory(): EditProfileViewModelFactory {
        return EditProfileViewModelFactory()
    }
}