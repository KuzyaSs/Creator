package ru.ermakov.creator.di

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.domain.repository.AuthRepository
import ru.ermakov.creator.domain.repository.CategoryRepository
import ru.ermakov.creator.domain.repository.CreatorRepository
import ru.ermakov.creator.domain.repository.CreditGoalRepository
import ru.ermakov.creator.domain.repository.FileRepository
import ru.ermakov.creator.domain.repository.FollowRepository
import ru.ermakov.creator.domain.repository.SubscriptionRepository
import ru.ermakov.creator.domain.repository.TransactionRepository
import ru.ermakov.creator.domain.repository.UserRepository
import ru.ermakov.creator.domain.repository.UserSubscriptionRepository
import ru.ermakov.creator.domain.useCase.account.SignOutUseCase
import ru.ermakov.creator.domain.useCase.balance.GetUserTransactionAmountUseCase
import ru.ermakov.creator.domain.useCase.balance.SearchUserTransactionPageByUserIdUseCase
import ru.ermakov.creator.domain.useCase.blog.FollowUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCreatorByUserIdUseCase
import ru.ermakov.creator.domain.useCase.blog.GetFollowByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.blog.IsFollowerByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.blog.IsSubscriberUseCase
import ru.ermakov.creator.domain.useCase.blog.UnfollowUseCase
import ru.ermakov.creator.domain.useCase.changePassword.ChangePasswordUseCase
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoriesUseCase
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoryInListUseCase
import ru.ermakov.creator.domain.useCase.createCreditGoal.CreateCreditGoalUseCase
import ru.ermakov.creator.domain.useCase.createSubscription.CreateSubscriptionUseCase
import ru.ermakov.creator.domain.useCase.createSubscription.DeleteSubscriptionByIdUseCase
import ru.ermakov.creator.domain.useCase.creditGoals.CloseCreditGoalUseCase
import ru.ermakov.creator.domain.useCase.creditGoals.GetCreditGoalsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.donateToCreditGoal.InsertCreditGoalTransactionUseCase
import ru.ermakov.creator.domain.useCase.editCreditGoal.EditCreditGoalUseCase
import ru.ermakov.creator.domain.useCase.editCreditGoal.GetCreditGoalByIdUseCase
import ru.ermakov.creator.domain.useCase.shared.CancelUploadTaskUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCategoriesByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.shared.GetUserByIdUseCase
import ru.ermakov.creator.domain.useCase.shared.UpdateUserUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateBioUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateUserImageUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateUsernameUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UploadProfileFileUseCase
import ru.ermakov.creator.domain.useCase.editSubscription.EditSubscriptionUseCase
import ru.ermakov.creator.domain.useCase.editSubscription.GetSubscriptionByIdUseCase
import ru.ermakov.creator.domain.useCase.follows.SearchFollowPageByUserIdUseCase
import ru.ermakov.creator.domain.useCase.passwordRecovery.RecoverPasswordByEmailUseCase
import ru.ermakov.creator.domain.useCase.purchaseSubscription.PurchaseSubscriptionUseCase
import ru.ermakov.creator.domain.useCase.search.SearchCreatorPageBySearchQueryUseCase
import ru.ermakov.creator.domain.useCase.shared.GetBalanceByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetDateTimeUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignInUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignedInUseCase
import ru.ermakov.creator.domain.useCase.signUp.SignUpUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.GetSubscriptionsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.GetUserSubscriptionsByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.UnsubscribeUseCase
import ru.ermakov.creator.domain.useCase.shared.InsertUserTransactionUseCase
import ru.ermakov.creator.domain.useCase.topUp.ValidateCreditCardDetailsUseCase
import ru.ermakov.creator.domain.useCase.topUp.ValidateCreditCardNumberUseCase
import ru.ermakov.creator.domain.useCase.topUp.ValidateCvvUseCase
import ru.ermakov.creator.domain.useCase.topUp.ValidateValidityUseCase

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
    fun provideGetCategoriesByUserIdUseCase(
        categoryRepository: CategoryRepository
    ): GetCategoriesByUserIdUseCase {
        return GetCategoriesByUserIdUseCase(categoryRepository = categoryRepository)
    }

    @Provides
    fun provideUpdateCategoriesUseCase(
        categoryRepository: CategoryRepository
    ): UpdateCategoriesUseCase {
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
    fun provideFollowUseCase(followRepository: FollowRepository): FollowUseCase {
        return FollowUseCase(followRepository = followRepository)
    }

    @Provides
    fun provideUnfollowUseCase(
        getFollowByUserAndCreatorIdsUseCase: GetFollowByUserAndCreatorIdsUseCase,
        followRepository: FollowRepository
    ): UnfollowUseCase {
        return UnfollowUseCase(
            getFollowByUserAndCreatorIdsUseCase = getFollowByUserAndCreatorIdsUseCase,
            followRepository = followRepository
        )
    }

    @Provides
    fun provideIsFollowerByUserAndCreatorIdsUseCase(
        followRepository: FollowRepository
    ): IsFollowerByUserAndCreatorIdsUseCase {
        return IsFollowerByUserAndCreatorIdsUseCase(followRepository = followRepository)
    }

    @Provides
    fun provideGetFollowByUserAndCreatorIdsUseCase(
        followRepository: FollowRepository
    ): GetFollowByUserAndCreatorIdsUseCase {
        return GetFollowByUserAndCreatorIdsUseCase(followRepository = followRepository)
    }

    @Provides
    fun provideIsSubscriberUseCase(): IsSubscriberUseCase {
        return IsSubscriberUseCase()
    }

    @Provides
    fun provideGetCreatorByUserIdUseCase(
        creatorRepository: CreatorRepository
    ): GetCreatorByUserIdUseCase {
        return GetCreatorByUserIdUseCase(creatorRepository = creatorRepository)
    }

    @Provides
    fun provideSearchCreatorPageBySearchQueryUseCase(
        creatorRepository: CreatorRepository
    ): SearchCreatorPageBySearchQueryUseCase {
        return SearchCreatorPageBySearchQueryUseCase(creatorRepository = creatorRepository)
    }

    @Provides
    fun provideGetSubscriptionsByCreatorIdUseCase(
        subscriptionRepository: SubscriptionRepository
    ): GetSubscriptionsByCreatorIdUseCase {
        return GetSubscriptionsByCreatorIdUseCase(subscriptionRepository = subscriptionRepository)
    }

    @Provides
    fun provideGetCreditGoalsByCreatorIdUseCase(
        creditGoalRepository: CreditGoalRepository
    ): GetCreditGoalsByCreatorIdUseCase {
        return GetCreditGoalsByCreatorIdUseCase(creditGoalRepository = creditGoalRepository)
    }

    @Provides
    fun provideCreateCreditGoalUseCase(
        creditGoalRepository: CreditGoalRepository
    ): CreateCreditGoalUseCase {
        return CreateCreditGoalUseCase(creditGoalRepository = creditGoalRepository)
    }

    @Provides
    fun provideEditCreditGoalUseCase(
        creditGoalRepository: CreditGoalRepository
    ): EditCreditGoalUseCase {
        return EditCreditGoalUseCase(creditGoalRepository = creditGoalRepository)
    }

    @Provides
    fun provideGetCreditGoalByIdUseCase(
        creditGoalRepository: CreditGoalRepository
    ): GetCreditGoalByIdUseCase {
        return GetCreditGoalByIdUseCase(creditGoalRepository = creditGoalRepository)
    }

    @Provides
    fun provideCloseCreditGoalUseCase(
        transactionRepository: TransactionRepository
    ): CloseCreditGoalUseCase {
        return CloseCreditGoalUseCase(transactionRepository = transactionRepository)
    }

    @Provides
    fun provideCreateSubscriptionUseCase(
        subscriptionRepository: SubscriptionRepository
    ): CreateSubscriptionUseCase {
        return CreateSubscriptionUseCase(subscriptionRepository = subscriptionRepository)
    }

    @Provides
    fun provideGetSubscriptionByIdUseCase(
        subscriptionRepository: SubscriptionRepository
    ): GetSubscriptionByIdUseCase {
        return GetSubscriptionByIdUseCase(subscriptionRepository = subscriptionRepository)
    }

    @Provides
    fun provideEditSubscriptionUseCase(
        subscriptionRepository: SubscriptionRepository
    ): EditSubscriptionUseCase {
        return EditSubscriptionUseCase(subscriptionRepository = subscriptionRepository)
    }

    @Provides
    fun provideGetUserSubscriptionsByUserAndCreatorIdsUseCase(
        userSubscriptionRepository: UserSubscriptionRepository
    ): GetUserSubscriptionsByUserAndCreatorIdsUseCase {
        return GetUserSubscriptionsByUserAndCreatorIdsUseCase(
            userSubscriptionRepository = userSubscriptionRepository
        )
    }

    @Provides
    fun provideUnsubscribeUseCase(
        userSubscriptionRepository: UserSubscriptionRepository
    ): UnsubscribeUseCase {
        return UnsubscribeUseCase(userSubscriptionRepository = userSubscriptionRepository)
    }

    @Provides
    fun provideDeleteSubscriptionByIdUseCase(
        subscriptionRepository: SubscriptionRepository
    ): DeleteSubscriptionByIdUseCase {
        return DeleteSubscriptionByIdUseCase(subscriptionRepository = subscriptionRepository)
    }

    @Provides
    fun providePurchaseSubscriptionUseCase(
        userSubscriptionRepository: UserSubscriptionRepository
    ): PurchaseSubscriptionUseCase {
        return PurchaseSubscriptionUseCase(userSubscriptionRepository = userSubscriptionRepository)
    }

    @Provides
    fun provideGetBalanceByUserIdUseCase(
        transactionRepository: TransactionRepository
    ): GetBalanceByUserIdUseCase {
        return GetBalanceByUserIdUseCase(transactionRepository = transactionRepository)
    }

    @Provides
    fun provideSearchFollowPageByUserIdUseCase(
        followRepository: FollowRepository
    ): SearchFollowPageByUserIdUseCase {
        return SearchFollowPageByUserIdUseCase(followRepository = followRepository)
    }

    @Provides
    fun provideInsertUserTransactionUseCase(
        transactionRepository: TransactionRepository
    ): InsertUserTransactionUseCase {
        return InsertUserTransactionUseCase(transactionRepository = transactionRepository)
    }

    @Provides
    fun provideGetUserTransactionAmountUseCase(): GetUserTransactionAmountUseCase {
        return GetUserTransactionAmountUseCase()
    }

    @Provides
    fun provideGetDateTimeUseCase(): GetDateTimeUseCase {
        return GetDateTimeUseCase()
    }

    @Provides
    fun provideSearchUserTransactionPageByUserIdUseCase(
        transactionRepository: TransactionRepository,
        getUserTransactionAmountUseCase: GetUserTransactionAmountUseCase,
        getDateTimeUseCase: GetDateTimeUseCase
    ): SearchUserTransactionPageByUserIdUseCase {
        return SearchUserTransactionPageByUserIdUseCase(
            transactionRepository = transactionRepository,
            getUserTransactionAmountUseCase = getUserTransactionAmountUseCase,
            getDateTimeUseCase = getDateTimeUseCase
        )
    }

    @Provides
    fun provideValidateCreditCardDetailsUseCase(
        validateCreditCardNumberUseCase: ValidateCreditCardNumberUseCase,
        validateValidityUseCase: ValidateValidityUseCase,
        validateCvvUseCase: ValidateCvvUseCase
    ): ValidateCreditCardDetailsUseCase {
        return ValidateCreditCardDetailsUseCase(
            validateCreditCardNumberUseCase = validateCreditCardNumberUseCase,
            validateValidityUseCase = validateValidityUseCase,
            validateCvvUseCase = validateCvvUseCase
        )
    }

    @Provides
    fun provideValidateCreditCardNumberUseCase(): ValidateCreditCardNumberUseCase {
        return ValidateCreditCardNumberUseCase()
    }

    @Provides
    fun provideValidateValidityUseCase(): ValidateValidityUseCase {
        return ValidateValidityUseCase()
    }

    @Provides
    fun provideValidateCvvUseCase(): ValidateCvvUseCase {
        return ValidateCvvUseCase()
    }

    @Provides
    fun provideInsertCreditGoalTransactionUseCase(
        transactionRepository: TransactionRepository
    ): InsertCreditGoalTransactionUseCase {
        return InsertCreditGoalTransactionUseCase(transactionRepository = transactionRepository)
    }
}