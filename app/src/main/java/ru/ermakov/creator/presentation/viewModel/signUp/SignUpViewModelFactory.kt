package ru.ermakov.creator.presentation.viewModel.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.signUp.SignUpUseCase

class SignUpViewModelFactory(private val signUpUseCase: SignUpUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignUpViewModel(signUpUseCase = signUpUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}