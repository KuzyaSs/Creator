package ru.ermakov.creator.di.presentation

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.domain.useCase.signUp.SignUpUseCase
import ru.ermakov.creator.presentation.viewModel.signIn.SignInViewModelFactory
import ru.ermakov.creator.presentation.viewModel.signUp.SignUpViewModelFactory

@Module
class ViewModelFactoryModule {
    @Provides
    fun provideSignInViewModelFactory(): SignInViewModelFactory {
        return SignInViewModelFactory()
    }

    @Provides
    fun provideSignUpViewModelFactory(signUpUseCase: SignUpUseCase): SignUpViewModelFactory {
        return SignUpViewModelFactory(signUpUseCase = signUpUseCase)
    }
}