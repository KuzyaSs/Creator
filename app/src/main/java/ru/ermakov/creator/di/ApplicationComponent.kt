package ru.ermakov.creator.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.ermakov.creator.di.data.LocalModule
import ru.ermakov.creator.di.data.RemoteModule
import ru.ermakov.creator.di.data.RepositoryModule
import ru.ermakov.creator.di.domain.UseCaseModule
import ru.ermakov.creator.di.presentation.ViewModelFactoryModule
import ru.ermakov.creator.presentation.activity.CreatorActivity
import ru.ermakov.creator.presentation.fragment.account.AccountFragment
import ru.ermakov.creator.presentation.fragment.ChatsFragment
import ru.ermakov.creator.presentation.fragment.FeedFragment
import ru.ermakov.creator.presentation.fragment.account.AccountSettingsFragment
import ru.ermakov.creator.presentation.fragment.account.EditProfileFragment
import ru.ermakov.creator.presentation.fragment.auth.PasswordRecoveryFragment
import ru.ermakov.creator.presentation.fragment.auth.SplashFragment
import ru.ermakov.creator.presentation.fragment.auth.SignInFragment
import ru.ermakov.creator.presentation.fragment.auth.SignUpFragment
import javax.inject.Qualifier
import javax.inject.Singleton

@Component(
    modules = [
        LocalModule::class,
        RemoteModule::class,
        RepositoryModule::class,
        UseCaseModule::class,
        ViewModelFactoryModule::class
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