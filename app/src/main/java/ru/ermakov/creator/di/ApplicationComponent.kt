package ru.ermakov.creator.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import layout.PostTypeFilterFragment
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.balance.BalanceFragment
import ru.ermakov.creator.presentation.screen.blog.CreatorBioFragment
import ru.ermakov.creator.presentation.screen.blog.BlogFragment
import ru.ermakov.creator.presentation.screen.changePassword.ChangePasswordFragment
import ru.ermakov.creator.presentation.screen.chooseCategory.ChooseCategoryFragment
import ru.ermakov.creator.presentation.screen.createCreditGoal.CreateCreditGoalFragment
import ru.ermakov.creator.presentation.screen.createSubscription.CreateSubscriptionFragment
import ru.ermakov.creator.presentation.screen.following.AccountFragment
import ru.ermakov.creator.presentation.screen.discover.DiscoverFragment
import ru.ermakov.creator.presentation.screen.editProfile.EditBioFragment
import ru.ermakov.creator.presentation.screen.following.FollowingFragment
import ru.ermakov.creator.presentation.screen.settings.SettingsFragment
import ru.ermakov.creator.presentation.screen.editProfile.EditProfileFragment
import ru.ermakov.creator.presentation.screen.editProfile.EditUsernameFragment
import ru.ermakov.creator.presentation.screen.editSubscription.EditSubscriptionFragment
import ru.ermakov.creator.presentation.screen.follows.FollowsFragment
import ru.ermakov.creator.presentation.screen.creditGoals.CreditGoalsFragment
import ru.ermakov.creator.presentation.screen.donateToCreditGoal.DonateToCreditGoalFragment
import ru.ermakov.creator.presentation.screen.editCreditGoal.EditCreditGoalFragment
import ru.ermakov.creator.presentation.screen.following.FeedFilterFragment
import ru.ermakov.creator.presentation.screen.passwordRecovery.PasswordRecoveryFragment
import ru.ermakov.creator.presentation.screen.purchaseSubscription.PurchaseSubscriptionFragment
import ru.ermakov.creator.presentation.screen.search.SearchFragment
import ru.ermakov.creator.presentation.screen.search.searchCreator.SearchCreatorFragment
import ru.ermakov.creator.presentation.screen.search.searchPost.SearchPostFragment
import ru.ermakov.creator.presentation.screen.splash.SplashFragment
import ru.ermakov.creator.presentation.screen.signIn.SignInFragment
import ru.ermakov.creator.presentation.screen.signUp.SignUpFragment
import ru.ermakov.creator.presentation.screen.subscriptions.SubscriptionsFragment
import ru.ermakov.creator.presentation.screen.tip.TipFragment
import ru.ermakov.creator.presentation.screen.topUp.TopUpFragment
import ru.ermakov.creator.presentation.screen.withdrawal.WithdrawalFragment
import javax.inject.Singleton

@Component(
    modules = [
        LocalModule::class,
        RemoteModule::class,
        RepositoryModule::class,
        UseCaseModule::class,
        ViewModelFactoryModule::class,
        ExceptionModule::class
    ]
)
@Singleton
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    // Activity.
    fun inject(activity: CreatorActivity)

    // Fragments.
    fun inject(fragment: SplashFragment)
    fun inject(fragment: SignInFragment)
    fun inject(fragment: SignUpFragment)
    fun inject(fragment: PasswordRecoveryFragment)
    fun inject(fragment: FollowingFragment)
    fun inject(fragment: FollowsFragment)
    fun inject(fragment: DiscoverFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: SearchCreatorFragment)
    fun inject(fragment: SearchPostFragment)
    fun inject(fragment: AccountFragment)
    fun inject(fragment: FeedFilterFragment)
    fun inject(fragment: PostTypeFilterFragment)
    fun inject(fragment: BalanceFragment)
    fun inject(fragment: TopUpFragment)
    fun inject(fragment: WithdrawalFragment)
    fun inject(fragment: BlogFragment)
    fun inject(fragment: TipFragment)
    fun inject(fragment: SubscriptionsFragment)
    fun inject(fragment: CreditGoalsFragment)
    fun inject(fragment: CreateCreditGoalFragment)
    fun inject(fragment: EditCreditGoalFragment)
    fun inject(fragment: DonateToCreditGoalFragment)
    fun inject(fragment: CreateSubscriptionFragment)
    fun inject(fragment: EditSubscriptionFragment)
    fun inject(fragment: PurchaseSubscriptionFragment)
    fun inject(fragment: CreatorBioFragment)
    fun inject(fragment: SettingsFragment)
    fun inject(fragment: EditProfileFragment)
    fun inject(fragment: EditUsernameFragment)
    fun inject(fragment: EditBioFragment)
    fun inject(fragment: ChooseCategoryFragment)
    fun inject(fragment: ChangePasswordFragment)
}