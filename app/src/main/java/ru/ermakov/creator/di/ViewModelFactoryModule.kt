package ru.ermakov.creator.di

import dagger.Module
import dagger.Provides
import ru.ermakov.creator.domain.useCase.account.SignOutUseCase
import ru.ermakov.creator.domain.useCase.balance.GetUserTransactionPageByUserIdUseCase
import ru.ermakov.creator.domain.useCase.blog.FollowUseCase
import ru.ermakov.creator.domain.useCase.blog.GetFilteredPostPageByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.blog.GetTagsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCreatorByUserIdUseCase
import ru.ermakov.creator.domain.useCase.blog.IsFollowerByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.blog.IsSubscriberUseCase
import ru.ermakov.creator.domain.useCase.blog.UnfollowUseCase
import ru.ermakov.creator.domain.useCase.changePassword.ChangePasswordUseCase
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoriesUseCase
import ru.ermakov.creator.domain.useCase.chooseCategory.UpdateCategoryInListUseCase
import ru.ermakov.creator.domain.useCase.createCreditGoal.CreateCreditGoalUseCase
import ru.ermakov.creator.domain.useCase.createPost.PublishPostUseCase
import ru.ermakov.creator.domain.useCase.createSubscription.CreateSubscriptionUseCase
import ru.ermakov.creator.domain.useCase.createSubscription.DeleteSubscriptionByIdUseCase
import ru.ermakov.creator.domain.useCase.createTag.InsertTagUseCase
import ru.ermakov.creator.domain.useCase.creditGoals.CloseCreditGoalUseCase
import ru.ermakov.creator.domain.useCase.creditGoals.GetCreditGoalsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.discover.GetFilteredPostPageByUserIdUseCase
import ru.ermakov.creator.domain.useCase.donateToCreditGoal.InsertCreditGoalTransactionUseCase
import ru.ermakov.creator.domain.useCase.editCreditGoal.EditCreditGoalUseCase
import ru.ermakov.creator.domain.useCase.editCreditGoal.GetCreditGoalByIdUseCase
import ru.ermakov.creator.domain.useCase.shared.CancelUploadTaskUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCategoriesByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateBioUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateUserImageUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateUsernameUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UploadProfileFileUseCase
import ru.ermakov.creator.domain.useCase.editSubscription.EditSubscriptionUseCase
import ru.ermakov.creator.domain.useCase.editSubscription.GetSubscriptionByIdUseCase
import ru.ermakov.creator.domain.useCase.editTag.EditTagUseCase
import ru.ermakov.creator.domain.useCase.editTag.GetTagByIdUseCase
import ru.ermakov.creator.domain.useCase.following.DeleteLikeFromPostUseCase
import ru.ermakov.creator.domain.useCase.following.DeletePostByIdUseCase
import ru.ermakov.creator.domain.useCase.following.GetAllCategoriesUseCase
import ru.ermakov.creator.domain.useCase.following.GetFilteredFollowingPostPageByUserIdUseCase
import ru.ermakov.creator.domain.useCase.following.GetPostByUserAndPostIdsUseCase
import ru.ermakov.creator.domain.useCase.following.InsertLikeToPostUseCase
import ru.ermakov.creator.domain.useCase.follows.SearchFollowPageByUserIdUseCase
import ru.ermakov.creator.domain.useCase.passwordRecovery.RecoverPasswordByEmailUseCase
import ru.ermakov.creator.domain.useCase.purchaseSubscription.PurchaseSubscriptionUseCase
import ru.ermakov.creator.domain.useCase.search.GetPostPageByUserIdAndSearchQueryUseCase
import ru.ermakov.creator.domain.useCase.search.SearchCreatorPageBySearchQueryUseCase
import ru.ermakov.creator.domain.useCase.shared.GetBalanceByUserIdUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignInUseCase
import ru.ermakov.creator.domain.useCase.signIn.SignedInUseCase
import ru.ermakov.creator.domain.useCase.signUp.SignUpUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.GetSubscriptionsByCreatorIdUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.GetUserSubscriptionsByUserAndCreatorIdsUseCase
import ru.ermakov.creator.domain.useCase.subscriptions.UnsubscribeUseCase
import ru.ermakov.creator.domain.useCase.shared.InsertUserTransactionUseCase
import ru.ermakov.creator.domain.useCase.tags.DeleteTagByIdUseCase
import ru.ermakov.creator.domain.useCase.topUp.ValidateCreditCardDetailsUseCase
import ru.ermakov.creator.domain.useCase.topUp.ValidateCreditCardNumberUseCase
import ru.ermakov.creator.presentation.screen.balance.BalanceViewModelFactory
import ru.ermakov.creator.presentation.screen.blog.BlogViewModelFactory
import ru.ermakov.creator.presentation.screen.changePassword.ChangePasswordViewModelFactory
import ru.ermakov.creator.presentation.screen.chooseCategory.ChooseCategoryViewModelFactory
import ru.ermakov.creator.presentation.screen.createCreditGoal.CreateCreditGoalViewModelFactory
import ru.ermakov.creator.presentation.screen.createPost.CreatePostViewModelFactory
import ru.ermakov.creator.presentation.screen.createSubscription.CreateSubscriptionViewModelFactory
import ru.ermakov.creator.presentation.screen.createTag.CreateTagViewModelFactory
import ru.ermakov.creator.presentation.screen.editProfile.EditProfileViewModelFactory
import ru.ermakov.creator.presentation.screen.editSubscription.EditSubscriptionViewModelFactory
import ru.ermakov.creator.presentation.screen.following.FollowingViewModelFactory
import ru.ermakov.creator.presentation.screen.follows.FollowsViewModelFactory
import ru.ermakov.creator.presentation.screen.creditGoals.CreditGoalsViewModelFactory
import ru.ermakov.creator.presentation.screen.discover.DiscoverViewModelFactory
import ru.ermakov.creator.presentation.screen.donateToCreditGoal.DonateToCreditGoalViewModelFactory
import ru.ermakov.creator.presentation.screen.editCreditGoal.EditCreditGoalViewModelFactory
import ru.ermakov.creator.presentation.screen.editTag.EditTagViewModelFactory
import ru.ermakov.creator.presentation.screen.passwordRecovery.PasswordRecoveryViewModelFactory
import ru.ermakov.creator.presentation.screen.purchaseSubscription.PurchaseSubscriptionViewModelFactory
import ru.ermakov.creator.presentation.screen.search.searchCreator.SearchCreatorViewModelFactory
import ru.ermakov.creator.presentation.screen.search.searchPost.SearchPostViewModelFactory
import ru.ermakov.creator.presentation.screen.signIn.SignInViewModelFactory
import ru.ermakov.creator.presentation.screen.signUp.SignUpViewModelFactory
import ru.ermakov.creator.presentation.screen.splash.SplashViewModelFactory
import ru.ermakov.creator.presentation.screen.subscriptions.SubscriptionsViewModelFactory
import ru.ermakov.creator.presentation.screen.tags.TagsViewModelFactory
import ru.ermakov.creator.presentation.screen.tip.TipViewModelFactory
import ru.ermakov.creator.presentation.screen.topUp.TopUpViewModelFactory
import ru.ermakov.creator.presentation.screen.withdrawal.WithdrawalViewModelFactory
import ru.ermakov.creator.presentation.util.ExceptionHandler

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
        recoverPasswordByEmailUseCase: RecoverPasswordByEmailUseCase,
        exceptionHandler: ExceptionHandler
    ): PasswordRecoveryViewModelFactory {
        return PasswordRecoveryViewModelFactory(
            recoverPasswordByEmailUseCase = recoverPasswordByEmailUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideFollowingViewModelFactory(
        getCurrentUserUseCase: GetCurrentUserUseCase,
        getAllCategoriesUseCase: GetAllCategoriesUseCase,
        getFilteredFollowingPostPageByUserIdUseCase: GetFilteredFollowingPostPageByUserIdUseCase,
        updateCategoryInListUseCase: UpdateCategoryInListUseCase,
        deletePostByIdUseCase: DeletePostByIdUseCase,
        getPostByUserAndPostIdsUseCase: GetPostByUserAndPostIdsUseCase,
        insertLikeToPostUseCase: InsertLikeToPostUseCase,
        deleteLikeFromPostUseCase: DeleteLikeFromPostUseCase,
        signOutUseCase: SignOutUseCase,
        exceptionHandler: ExceptionHandler
    ): FollowingViewModelFactory {
        return FollowingViewModelFactory(
            getCurrentUserUseCase = getCurrentUserUseCase,
            getAllCategoriesUseCase = getAllCategoriesUseCase,
            getFilteredFollowingPostPageByUserIdUseCase = getFilteredFollowingPostPageByUserIdUseCase,
            updateCategoryInListUseCase = updateCategoryInListUseCase,
            deletePostByIdUseCase = deletePostByIdUseCase,
            getPostByUserAndPostIdsUseCase = getPostByUserAndPostIdsUseCase,
            insertLikeToPostUseCase = insertLikeToPostUseCase,
            deleteLikeFromPostUseCase = deleteLikeFromPostUseCase,
            signOutUseCase = signOutUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideBlogViewModelFactory(
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        isFollowerByUserAndCreatorIdsUseCase: IsFollowerByUserAndCreatorIdsUseCase,
        followUseCase: FollowUseCase,
        unfollowUseCase: UnfollowUseCase,
        isSubscriberUseCase: IsSubscriberUseCase,
        getCreatorByUserIdUseCase: GetCreatorByUserIdUseCase,
        getFilteredPostPageByUserAndCreatorIdsUseCase: GetFilteredPostPageByUserAndCreatorIdsUseCase,
        getTagsByCreatorId: GetTagsByCreatorIdUseCase,
        deletePostByIdUseCase: DeletePostByIdUseCase,
        getPostByUserAndPostIdsUseCase: GetPostByUserAndPostIdsUseCase,
        insertLikeToPostUseCase: InsertLikeToPostUseCase,
        deleteLikeFromPostUseCase: DeleteLikeFromPostUseCase,
        exceptionHandler: ExceptionHandler,
    ): BlogViewModelFactory {
        return BlogViewModelFactory(
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            isFollowerByUserAndCreatorIdsUseCase = isFollowerByUserAndCreatorIdsUseCase,
            followUseCase = followUseCase,
            unfollowUseCase = unfollowUseCase,
            isSubscriberUseCase = isSubscriberUseCase,
            getCreatorByUserIdUseCase = getCreatorByUserIdUseCase,
            getFilteredPostPageByUserAndCreatorIdsUseCase = getFilteredPostPageByUserAndCreatorIdsUseCase,
            getTagsByCreatorId = getTagsByCreatorId,
            deletePostByIdUseCase = deletePostByIdUseCase,
            getPostByUserAndPostIdsUseCase = getPostByUserAndPostIdsUseCase,
            insertLikeToPostUseCase = insertLikeToPostUseCase,
            deleteLikeFromPostUseCase = deleteLikeFromPostUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideSearchCreatorViewModelFactory(
        searchCreatorPageBySearchQueryUseCase: SearchCreatorPageBySearchQueryUseCase,
        exceptionHandler: ExceptionHandler
    ): SearchCreatorViewModelFactory {
        return SearchCreatorViewModelFactory(
            searchCreatorPageBySearchQueryUseCase = searchCreatorPageBySearchQueryUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideSearchPostViewModelFactory(
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        getPostPageByUserIdAndSearchQueryUseCase: GetPostPageByUserIdAndSearchQueryUseCase,
        deletePostByIdUseCase: DeletePostByIdUseCase,
        getPostByUserAndPostIdsUseCase: GetPostByUserAndPostIdsUseCase,
        insertLikeToPostUseCase: InsertLikeToPostUseCase,
        deleteLikeFromPostUseCase: DeleteLikeFromPostUseCase,
        exceptionHandler: ExceptionHandler
    ): SearchPostViewModelFactory {
        return SearchPostViewModelFactory(
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            getPostPageByUserIdAndSearchQueryUseCase = getPostPageByUserIdAndSearchQueryUseCase,
            deletePostByIdUseCase = deletePostByIdUseCase,
            getPostByUserAndPostIdsUseCase = getPostByUserAndPostIdsUseCase,
            insertLikeToPostUseCase = insertLikeToPostUseCase,
            deleteLikeFromPostUseCase = deleteLikeFromPostUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideEditProfileViewModelFactory(
        getCurrentUserUseCase: GetCurrentUserUseCase,
        updateUserImageUseCase: UpdateUserImageUseCase,
        updateUsernameUseCase: UpdateUsernameUseCase,
        updateBioUseCase: UpdateBioUseCase,
        uploadProfileFileUseCase: UploadProfileFileUseCase,
        cancelUploadTaskUseCase: CancelUploadTaskUseCase,
        exceptionHandler: ExceptionHandler
    ): EditProfileViewModelFactory {
        return EditProfileViewModelFactory(
            getCurrentUserUseCase = getCurrentUserUseCase,
            updateUserImageUseCase = updateUserImageUseCase,
            updateUsernameUseCase = updateUsernameUseCase,
            updateBioUseCase = updateBioUseCase,
            uploadProfileFileUseCase = uploadProfileFileUseCase,
            cancelUploadTaskUseCase = cancelUploadTaskUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideChooseCategoryViewModelFactory(
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        getCategoriesByUserIdUseCase: GetCategoriesByUserIdUseCase,
        updateCategoriesUseCase: UpdateCategoriesUseCase,
        updateCategoryInListUseCase: UpdateCategoryInListUseCase,
        exceptionHandler: ExceptionHandler
    ): ChooseCategoryViewModelFactory {
        return ChooseCategoryViewModelFactory(
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            getCategoriesByUserIdUseCase = getCategoriesByUserIdUseCase,
            updateCategoriesUseCase = updateCategoriesUseCase,
            updateCategoryInListUseCase = updateCategoryInListUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideChangePasswordViewModelFactory(
        changePasswordUseCase: ChangePasswordUseCase,
        exceptionHandler: ExceptionHandler
    ): ChangePasswordViewModelFactory {
        return ChangePasswordViewModelFactory(
            changePasswordUseCase = changePasswordUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideSubscriptionsViewModelFactory(
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        getUserSubscriptionsByUserAndCreatorIdsUseCase: GetUserSubscriptionsByUserAndCreatorIdsUseCase,
        getSubscriptionsByCreatorIdUseCase: GetSubscriptionsByCreatorIdUseCase,
        unsubscribeUseCase: UnsubscribeUseCase,
        deleteSubscriptionByIdUseCase: DeleteSubscriptionByIdUseCase,
        exceptionHandler: ExceptionHandler
    ): SubscriptionsViewModelFactory {
        return SubscriptionsViewModelFactory(
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            getUserSubscriptionsByUserAndCreatorIdsUseCase = getUserSubscriptionsByUserAndCreatorIdsUseCase,
            getSubscriptionsByCreatorIdUseCase = getSubscriptionsByCreatorIdUseCase,
            unsubscribeUseCase = unsubscribeUseCase,
            deleteSubscriptionByIdUseCase = deleteSubscriptionByIdUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideCreditGoalsViewModelFactory(
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        getCreditGoalsByCreatorIdUseCase: GetCreditGoalsByCreatorIdUseCase,
        closeCreditGoalUseCase: CloseCreditGoalUseCase,
        exceptionHandler: ExceptionHandler
    ): CreditGoalsViewModelFactory {
        return CreditGoalsViewModelFactory(
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            getCreditGoalsByCreatorIdUseCase = getCreditGoalsByCreatorIdUseCase,
            closeCreditGoalUseCase = closeCreditGoalUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideCreateCreditGoalViewModelFactory(
        createCreditGoalUseCase: CreateCreditGoalUseCase,
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        exceptionHandler: ExceptionHandler
    ): CreateCreditGoalViewModelFactory {
        return CreateCreditGoalViewModelFactory(
            createCreditGoalUseCase = createCreditGoalUseCase,
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideEditCreditGoalViewModelFactory(
        getCreditGoalByIdUseCase: GetCreditGoalByIdUseCase,
        editCreditGoalUseCase: EditCreditGoalUseCase,
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        exceptionHandler: ExceptionHandler
    ): EditCreditGoalViewModelFactory {
        return EditCreditGoalViewModelFactory(
            getCreditGoalByIdUseCase = getCreditGoalByIdUseCase,
            editCreditGoalUseCase = editCreditGoalUseCase,
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideCreateSubscriptionViewModelFactory(
        createSubscriptionUseCase: CreateSubscriptionUseCase,
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        exceptionHandler: ExceptionHandler
    ): CreateSubscriptionViewModelFactory {
        return CreateSubscriptionViewModelFactory(
            createSubscriptionUseCase = createSubscriptionUseCase,
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideEditSubscriptionViewModelFactory(
        getSubscriptionByIdUseCase: GetSubscriptionByIdUseCase,
        editSubscriptionUseCase: EditSubscriptionUseCase,
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        exceptionHandler: ExceptionHandler
    ): EditSubscriptionViewModelFactory {
        return EditSubscriptionViewModelFactory(
            getSubscriptionByIdUseCase = getSubscriptionByIdUseCase,
            editSubscriptionUseCase = editSubscriptionUseCase,
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun providePurchaseSubscriptionViewModelFactory(
        getSubscriptionByIdUseCase: GetSubscriptionByIdUseCase,
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        getBalanceByUserIdUseCase: GetBalanceByUserIdUseCase,
        purchaseSubscriptionUseCase: PurchaseSubscriptionUseCase,
        exceptionHandler: ExceptionHandler
    ): PurchaseSubscriptionViewModelFactory {
        return PurchaseSubscriptionViewModelFactory(
            getSubscriptionByIdUseCase = getSubscriptionByIdUseCase,
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            getBalanceByUserIdUseCase = getBalanceByUserIdUseCase,
            purchaseSubscriptionUseCase = purchaseSubscriptionUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideFollowsViewModelFactory(
        searchFollowPageByUserIdUseCase: SearchFollowPageByUserIdUseCase,
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        exceptionHandler: ExceptionHandler
    ): FollowsViewModelFactory {
        return FollowsViewModelFactory(
            searchFollowPageByUserIdUseCase = searchFollowPageByUserIdUseCase,
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideTipViewModelFactory(
        insertUserTransactionUseCase: InsertUserTransactionUseCase,
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        getBalanceByUserIdUseCase: GetBalanceByUserIdUseCase,
        exceptionHandler: ExceptionHandler
    ): TipViewModelFactory {
        return TipViewModelFactory(
            insertUserTransactionUseCase = insertUserTransactionUseCase,
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            getBalanceByUserIdUseCase = getBalanceByUserIdUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideBalanceViewModelFactory(
        getUserTransactionPageByUserIdUseCase: GetUserTransactionPageByUserIdUseCase,
        getBalanceByUserIdUseCase: GetBalanceByUserIdUseCase,
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        exceptionHandler: ExceptionHandler
    ): BalanceViewModelFactory {
        return BalanceViewModelFactory(
            getUserTransactionPageByUserIdUseCase = getUserTransactionPageByUserIdUseCase,
            getBalanceByUserIdUseCase = getBalanceByUserIdUseCase,
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            exceptionHandler = exceptionHandler,
        )
    }

    @Provides
    fun provideTopUpViewModelFactory(
        insertUserTransactionUseCase: InsertUserTransactionUseCase,
        validateCreditCardDetailsUseCase: ValidateCreditCardDetailsUseCase,
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        getBalanceByUserIdUseCase: GetBalanceByUserIdUseCase,
        exceptionHandler: ExceptionHandler
    ): TopUpViewModelFactory {
        return TopUpViewModelFactory(
            insertUserTransactionUseCase = insertUserTransactionUseCase,
            validateCreditCardDetailsUseCase = validateCreditCardDetailsUseCase,
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            getBalanceByUserIdUseCase = getBalanceByUserIdUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideWithdrawalViewModelFactory(
        insertUserTransactionUseCase: InsertUserTransactionUseCase,
        validateCreditCardNumberUseCase: ValidateCreditCardNumberUseCase,
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        getBalanceByUserIdUseCase: GetBalanceByUserIdUseCase,
        exceptionHandler: ExceptionHandler
    ): WithdrawalViewModelFactory {
        return WithdrawalViewModelFactory(
            insertUserTransactionUseCase = insertUserTransactionUseCase,
            validateCreditCardNumberUseCase = validateCreditCardNumberUseCase,
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            getBalanceByUserIdUseCase = getBalanceByUserIdUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideDonateToCreditGoalViewModelFactory(
        insertCreditGoalTransactionUseCase: InsertCreditGoalTransactionUseCase,
        getCreditGoalByIdUseCase: GetCreditGoalByIdUseCase,
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        getBalanceByUserIdUseCase: GetBalanceByUserIdUseCase,
        exceptionHandler: ExceptionHandler
    ): DonateToCreditGoalViewModelFactory {
        return DonateToCreditGoalViewModelFactory(
            insertCreditGoalTransactionUseCase = insertCreditGoalTransactionUseCase,
            getCreditGoalByIdUseCase = getCreditGoalByIdUseCase,
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            getBalanceByUserIdUseCase = getBalanceByUserIdUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideDiscoverViewModelFactory(
        getCurrentUserUseCase: GetCurrentUserUseCase,
        getAllCategoriesUseCase: GetAllCategoriesUseCase,
        getFilteredPostPageByUserIdUseCase: GetFilteredPostPageByUserIdUseCase,
        updateCategoryInListUseCase: UpdateCategoryInListUseCase,
        deletePostByIdUseCase: DeletePostByIdUseCase,
        getPostByUserAndPostIdsUseCase: GetPostByUserAndPostIdsUseCase,
        insertLikeToPostUseCase: InsertLikeToPostUseCase,
        deleteLikeFromPostUseCase: DeleteLikeFromPostUseCase,
        exceptionHandler: ExceptionHandler
    ): DiscoverViewModelFactory {
        return DiscoverViewModelFactory(
            getCurrentUserUseCase = getCurrentUserUseCase,
            getAllCategoriesUseCase = getAllCategoriesUseCase,
            getFilteredPostPageByUserIdUseCase = getFilteredPostPageByUserIdUseCase,
            updateCategoryInListUseCase = updateCategoryInListUseCase,
            deletePostByIdUseCase = deletePostByIdUseCase,
            getPostByUserAndPostIdsUseCase = getPostByUserAndPostIdsUseCase,
            insertLikeToPostUseCase = insertLikeToPostUseCase,
            deleteLikeFromPostUseCase = deleteLikeFromPostUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideCreatePostViewModelFactory(
        getTagsByCreatorIdUseCase: GetTagsByCreatorIdUseCase,
        getSubscriptionsByCreatorIdUseCase: GetSubscriptionsByCreatorIdUseCase,
        publishPostUseCase: PublishPostUseCase,
        exceptionHandler: ExceptionHandler
    ): CreatePostViewModelFactory {
        return CreatePostViewModelFactory(
            getTagsByCreatorIdUseCase = getTagsByCreatorIdUseCase,
            getSubscriptionsByCreatorIdUseCase = getSubscriptionsByCreatorIdUseCase,
            publishPostUseCase = publishPostUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideTagsViewModelFactory(
        getTagsByCreatorIdUseCase: GetTagsByCreatorIdUseCase,
        deleteTagByIdUseCase: DeleteTagByIdUseCase,
        exceptionHandler: ExceptionHandler
    ): TagsViewModelFactory {
        return TagsViewModelFactory(
            getTagsByCreatorIdUseCase = getTagsByCreatorIdUseCase,
            deleteTagByIdUseCase = deleteTagByIdUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideCreateTagViewModelFactory(
        insertTagUseCase: InsertTagUseCase,
        exceptionHandler: ExceptionHandler
    ): CreateTagViewModelFactory {
        return CreateTagViewModelFactory(
            insertTagUseCase = insertTagUseCase,
            exceptionHandler = exceptionHandler
        )
    }

    @Provides
    fun provideEditTagViewModelFactory(
        getTagByIdUseCase: GetTagByIdUseCase,
        editTagUseCase: EditTagUseCase,
        getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
        exceptionHandler: ExceptionHandler
    ): EditTagViewModelFactory {
        return EditTagViewModelFactory(
            getTagByIdUseCase = getTagByIdUseCase,
            editTagUseCase = editTagUseCase,
            getCurrentUserIdUseCase = getCurrentUserIdUseCase,
            exceptionHandler = exceptionHandler
        )
    }
}