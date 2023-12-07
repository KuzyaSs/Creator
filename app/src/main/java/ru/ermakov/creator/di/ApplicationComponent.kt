package ru.ermakov.creator.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.blog.CreatorBioFragment
import ru.ermakov.creator.presentation.screen.blog.BlogFragment
import ru.ermakov.creator.presentation.screen.changePassword.ChangePasswordFragment
import ru.ermakov.creator.presentation.screen.chooseCategory.ChooseCategoryFragment
import ru.ermakov.creator.presentation.screen.following.AccountFragment
import ru.ermakov.creator.presentation.screen.discover.DiscoverFragment
import ru.ermakov.creator.presentation.screen.editProfile.EditBioFragment
import ru.ermakov.creator.presentation.screen.following.FollowingFragment
import ru.ermakov.creator.presentation.screen.settings.SettingsFragment
import ru.ermakov.creator.presentation.screen.editProfile.EditProfileFragment
import ru.ermakov.creator.presentation.screen.editProfile.EditUsernameFragment
import ru.ermakov.creator.presentation.screen.passwordRecovery.PasswordRecoveryFragment
import ru.ermakov.creator.presentation.screen.search.SearchFragment
import ru.ermakov.creator.presentation.screen.search.searchCreator.SearchCreatorFragment
import ru.ermakov.creator.presentation.screen.search.searchPost.SearchPostFragment
import ru.ermakov.creator.presentation.screen.splash.SplashFragment
import ru.ermakov.creator.presentation.screen.signIn.SignInFragment
import ru.ermakov.creator.presentation.screen.signUp.SignUpFragment
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

    // Activity
    fun inject(activity: CreatorActivity)

    // Fragments
    fun inject(fragment: SplashFragment)
    fun inject(fragment: SignInFragment)
    fun inject(fragment: SignUpFragment)
    fun inject(fragment: PasswordRecoveryFragment)
    fun inject(fragment: FollowingFragment)
    fun inject(fragment: DiscoverFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: SearchCreatorFragment)
    fun inject(fragment: SearchPostFragment)
    fun inject(fragment: AccountFragment)
    fun inject(fragment: BlogFragment)
    fun inject(fragment: CreatorBioFragment)
    fun inject(fragment: SettingsFragment)
    fun inject(fragment: EditProfileFragment)
    fun inject(fragment: EditUsernameFragment)
    fun inject(fragment: EditBioFragment)
    fun inject(fragment: ChooseCategoryFragment)
    fun inject(fragment: ChangePasswordFragment)
}