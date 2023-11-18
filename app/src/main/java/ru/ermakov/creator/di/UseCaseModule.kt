package ru.ermakov.creator.di

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.data.remote.dataSource.FileRemoteDataSource
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.domain.repository.UserRepository
import ru.ermakov.creator.domain.useCase.account.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.account.SignOutUseCase
import ru.ermakov.creator.domain.useCase.editProfile.EditProfileAvatarUseCase
import ru.ermakov.creator.domain.useCase.passwordRecovery.RecoverPasswordByEmailUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignInUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignedInUseCase
import ru.ermakov.creator.domain.useCase.signUp.SignUpUseCase

@Module
class UseCaseModule {
    @Provides
    fun provideSignUpUseCase(
        authRepository: AuthRepository,
        userRepository: UserRepository
    ): SignUpUseCase {
        return SignUpUseCase(authRepository = authRepository, userRepository = userRepository)
    }

    @Provides
    fun provideSignInUseCase(authRepository: AuthRepository): SignInUseCase {
        return SignInUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideSignedInUseCase(authRepository: AuthRepository): SignedInUseCase {
        return SignedInUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideSignOutUseCase(authRepository: AuthRepository): SignOutUseCase {
        return SignOutUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideRecoverPasswordByEmailUseCase(authRepository: AuthRepository): RecoverPasswordByEmailUseCase {
        return RecoverPasswordByEmailUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideGetCurrentUserUseCase(
        authRepository: AuthRepository,
        userRepository: UserRepository
    ): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(
            authRepository = authRepository,
            userRepository = userRepository
        )
    }

    @Provides
    fun provideEditProfileAvatarUseCase(
        fileRemoteDataSource: FileRemoteDataSource,
        userRepository: UserRepository
    ): EditProfileAvatarUseCase {
        return EditProfileAvatarUseCase(
            fileRemoteDataSource = fileRemoteDataSource,
            userRepository = userRepository
        )
    }
}