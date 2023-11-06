package ru.ermakov.creator.presentation.screen.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.signIn.SignInUseCase

class SignInViewModelFactory(private val signInUseCase: SignInUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignInViewModel(signInUseCase = signInUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}