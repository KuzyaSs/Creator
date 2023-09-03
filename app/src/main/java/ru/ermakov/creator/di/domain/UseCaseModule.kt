package ru.ermakov.creator.di.domain

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.domain.useCase.auth.passwordRecovery.PasswordRecoveryUseCase
import ru.ermakov.creator.domain.useCase.auth.signIn.IsEmptySignInDataUseCase
import ru.ermakov.creator.domain.useCase.auth.signIn.SignInUseCase
import ru.ermakov.creator.domain.useCase.auth.signIn.SignedInUseCase
import ru.ermakov.creator.domain.useCase.auth.signUp.IsEmptySignUpDataUseCase
import ru.ermakov.creator.domain.useCase.auth.signUp.PasswordsAreTheSameUseCase
import ru.ermakov.creator.domain.useCase.auth.signUp.SignUpUseCase

@Module
class UseCaseModule {
    @Provides
    fun providePasswordRecoveryUseCase(authRepository: AuthRepository): PasswordRecoveryUseCase {
        return PasswordRecoveryUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideIsEmptySignInDataUseCase(): IsEmptySignInDataUseCase {
        return IsEmptySignInDataUseCase()
    }

    @Provides
    fun provideSignedInUseCase(authRepository: AuthRepository): SignedInUseCase {
        return SignedInUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideSignInUseCase(
        authRepository: AuthRepository,
        isEmptySignInDataUseCase: IsEmptySignInDataUseCase,
    ): SignInUseCase {
        return SignInUseCase(
            authRepository = authRepository,
            isEmptySignInDataUseCase = isEmptySignInDataUseCase
        )
    }

    @Provides
    fun provideIsEmptySignUpDataUseCase(): IsEmptySignUpDataUseCase {
        return IsEmptySignUpDataUseCase()
    }

    @Provides
    fun providePasswordsAreTheSameUseCase(): PasswordsAreTheSameUseCase {
        return PasswordsAreTheSameUseCase()
    }

    @Provides
    fun provideSignUpUseCase(
        authRepository: AuthRepository,
        isEmptySignUpDataUseCase: IsEmptySignUpDataUseCase,
        passwordsAreTheSameUseCase: PasswordsAreTheSameUseCase
    ): SignUpUseCase {
        return SignUpUseCase(
            authRepository = authRepository,
            isEmptySignUpDataUseCase = isEmptySignUpDataUseCase,
            passwordsAreTheSameUseCase = passwordsAreTheSameUseCase
        )
    }
}