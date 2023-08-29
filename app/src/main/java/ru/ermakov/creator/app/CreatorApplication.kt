package ru.ermakov.creator.app

import android.app.Application
import ru.ermakov.creator.di.ApplicationComponent
import ru.ermakov.creator.di.DaggerApplicationComponent

class CreatorApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(context = this)
    }
}