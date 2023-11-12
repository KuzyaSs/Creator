package ru.ermakov.creator.di

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.repository.user.auth.AuthRepository
import ru.ermakov.creator.domain.useCase.passwordRecovery.PasswordRecoveryUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignInUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignedInUseCase
import ru.ermakov.creator.domain.useCase.signUp.SignUpUseCase

@Module
class UseCaseModule {
    @Provides
    fun providePasswordRecoveryUseCase(authRepository: AuthRepository): PasswordRecoveryUseCase {
        return PasswordRecoveryUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideSignedInUseCase(authRepository: AuthRepository): SignedInUseCase {
        return SignedInUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideSignInUseCase(authRepository: AuthRepository): SignInUseCase {
        return SignInUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideSignUpUseCase(authRepository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(authRepository = authRepository)
    }
}