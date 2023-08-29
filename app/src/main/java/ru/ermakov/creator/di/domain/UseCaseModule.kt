package ru.ermakov.creator.di.domain

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.domain.useCase.signUp.PasswordsAreTheSameUseCase
import ru.ermakov.creator.domain.useCase.signUp.SignUpUseCase
import ru.ermakov.creator.domain.useCase.signUp.IsEmptyDataUseCase

@Module
class UseCaseModule {
    @Provides
    fun provideIsEmptyDataUseCase(): IsEmptyDataUseCase {
        return IsEmptyDataUseCase()
    }

    @Provides
    fun providePasswordsAreTheSameUseCase(): PasswordsAreTheSameUseCase {
        return PasswordsAreTheSameUseCase()
    }

    @Provides
    fun provideSignUpUseCase(
        authRepository: AuthRepository,
        isEmptyDataUseCase: IsEmptyDataUseCase,
        passwordsAreTheSameUseCase: PasswordsAreTheSameUseCase
    ): SignUpUseCase {
        return SignUpUseCase(
            authRepository = authRepository,
            isEmptyDataUseCase = isEmptyDataUseCase,
            passwordsAreTheSameUseCase = passwordsAreTheSameUseCase
        )
    }
}