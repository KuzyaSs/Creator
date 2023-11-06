package ru.ermakov.creator.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.signIn.SignedInUseCase

class SplashViewModelFactory(private val signedInUseCase: SignedInUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(signedInUseCase = signedInUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}