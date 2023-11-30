package ru.ermakov.creator.di

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.domain.repository.CategoryRepository
import ru.ermakov.creator.domain.repository.FileRepository
import ru.ermakov.creator.domain.repository.UserRepository
import ru.ermakov.creator.domain.useCase.account.SignOutUseCase
import ru.ermakov.creator.domain.useCase.blog.GetCreatorByIdUseCase
import ru.ermakov.creator.domain.useCase.blog.GetNumFollowersByUserIdUseCase
import ru.ermakov.creator.domain.useCase.blog.GetNumPostsByUserIdUseCase
import ru.ermakov.creator.domain.useCase.blog.GetNumSubscribersByUserIdUseCase
import ru.ermakov.creator.domain.useCase.blog.IsFollowerUseCase
import ru.ermakov.creator.domain.useCase.blog.IsSubscriberUseCase
import ru.ermakov.creator.domain.useCase.changePassword.ChangePasswordUseCase
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoriesUseCase
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoryInListUseCase
import ru.ermakov.creator.domain.useCase.common.CancelUploadTaskUseCase
import ru.ermakov.creator.domain.useCase.common.GetCategoriesByUserIdUseCase
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserIdUseCase
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.common.GetUserByIdUseCase
import ru.ermakov.creator.domain.useCase.common.UpdateUserUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateBioUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateUserImageUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateUsernameUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UploadProfileFileUseCase
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
    fun provideGetCurrentUserIdUseCase(authRepository: AuthRepository): GetCurrentUserIdUseCase {
        return GetCurrentUserIdUseCase(
            authRepository = authRepository,
        )
    }

    @Provides
    fun provideGetUserByIdUseCase(userRepository: UserRepository): GetUserByIdUseCase {
        return GetUserByIdUseCase(userRepository = userRepository)
    }

    @Provides
    fun provideGetCurrentUserUseCase(
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        userRepository: UserRepository
    ): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            userRepository = userRepository
        )
    }

    @Provides
    fun provideUpdateUserUseCase(userRepository: UserRepository): UpdateUserUseCase {
        return UpdateUserUseCase(userRepository = userRepository)
    }

    @Provides
    fun provideUpdateUserImageUseCase(
        getCurrentUserUseCase: GetCurrentUserUseCase,
        updateUserUseCase: UpdateUserUseCase
    ): UpdateUserImageUseCase {
        return UpdateUserImageUseCase(
            getCurrentUserUseCase = getCurrentUserUseCase,
            updateUserUseCase = updateUserUseCase
        )
    }

    @Provides
    fun provideUpdateUsernameUseCase(
        getCurrentUserUseCase: GetCurrentUserUseCase,
        updateUserUseCase: UpdateUserUseCase
    ): UpdateUsernameUseCase {
        return UpdateUsernameUseCase(
            getCurrentUserUseCase = getCurrentUserUseCase,
            updateUserUseCase = updateUserUseCase
        )
    }

    @Provides
    fun provideUpdateBioUseCase(
        getCurrentUserUseCase: GetCurrentUserUseCase,
        updateUserUseCase: UpdateUserUseCase
    ): UpdateBioUseCase {
        return UpdateBioUseCase(
            getCurrentUserUseCase = getCurrentUserUseCase,
            updateUserUseCase = updateUserUseCase
        )
    }

    @Provides
    fun provideUploadFileUseCase(fileRepository: FileRepository): UploadProfileFileUseCase {
        return UploadProfileFileUseCase(fileRepository = fileRepository)
    }

    @Provides
    fun provideCancelUploadTaskUseCase(fileRepository: FileRepository): CancelUploadTaskUseCase {
        return CancelUploadTaskUseCase(fileRepository = fileRepository)
    }

    @Provides
    fun provideGetCategoriesByUserIdUseCase(categoryRepository: CategoryRepository): GetCategoriesByUserIdUseCase {
        return GetCategoriesByUserIdUseCase(categoryRepository = categoryRepository)
    }

    @Provides
    fun provideUpdateCategoriesUseCase(categoryRepository: CategoryRepository): UpdateCategoriesUseCase {
        return UpdateCategoriesUseCase(categoryRepository = categoryRepository)
    }

    @Provides
    fun provideUpdateCategoryInListUseCase(): UpdateCategoryInListUseCase {
        return UpdateCategoryInListUseCase()
    }

    @Provides
    fun provideChangePasswordUseCase(authRepository: AuthRepository): ChangePasswordUseCase {
        return ChangePasswordUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideIsFollowerUseCase(): IsFollowerUseCase {
        return IsFollowerUseCase()
    }

    @Provides
    fun provideGetNumFollowersByUserIdUseCase(): GetNumFollowersByUserIdUseCase {
        return GetNumFollowersByUserIdUseCase()
    }

    @Provides
    fun provideIsSubscriberUseCase(): IsSubscriberUseCase {
        return IsSubscriberUseCase()
    }

    @Provides
    fun provideGetNumSubscribersByUserIdUseCase(): GetNumSubscribersByUserIdUseCase {
        return GetNumSubscribersByUserIdUseCase()
    }

    @Provides
    fun provideGetNumPostsByUserIdUseCase(): GetNumPostsByUserIdUseCase {
        return GetNumPostsByUserIdUseCase()
    }

    @Provides
    fun provideGetCreatorByIdUseCase(
        getUserByIdUseCase: GetUserByIdUseCase,
        getCategoriesByUserIdUseCase: GetCategoriesByUserIdUseCase,
        getNumFollowersByUserIdUseCase: GetNumFollowersByUserIdUseCase,
        getNumSubscribersByUserIdUseCase: GetNumSubscribersByUserIdUseCase,
        getNumPostsByUserIdUseCase: GetNumPostsByUserIdUseCase,
    ): GetCreatorByIdUseCase {
        return GetCreatorByIdUseCase(
            getUserByIdUseCase = getUserByIdUseCase,
            getCategoriesByUserIdUseCase = getCategoriesByUserIdUseCase,
            getNumFollowersByUserIdUseCase = getNumFollowersByUserIdUseCase,
            getNumSubscribersByUserIdUseCase = getNumSubscribersByUserIdUseCase,
            getNumPostsByUserIdUseCase = getNumPostsByUserIdUseCase
        )
    }
}