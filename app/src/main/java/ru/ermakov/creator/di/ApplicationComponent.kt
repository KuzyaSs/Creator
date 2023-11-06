package ru.ermakov.creator.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.account.AccountFragment
import ru.ermakov.creator.presentation.screen.chats.ChatsFragment
import ru.ermakov.creator.presentation.screen.feed.FeedFragment
import ru.ermakov.creator.presentation.screen.accountSettings.AccountSettingsFragment
import ru.ermakov.creator.presentation.screen.editProfile.EditProfileFragment
import ru.ermakov.creator.presentation.screen.passwordRecovery.PasswordRecoveryFragment
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
    fun inject(fragment: FeedFragment)
    fun inject(fragment: ChatsFragment)
    fun inject(fragment: AccountFragment)
    fun inject(fragment: AccountSettingsFragment)
    fun inject(fragment: EditProfileFragment)
}