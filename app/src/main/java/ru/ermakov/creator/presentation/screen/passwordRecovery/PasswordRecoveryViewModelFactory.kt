package ru.ermakov.creator.presentation.screen.passwordRecovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.passwordRecovery.PasswordRecoveryUseCase

class PasswordRecoveryViewModelFactory(
    private val passwordRecoveryUseCase: PasswordRecoveryUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordRecoveryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PasswordRecoveryViewModel(passwordRecoveryUseCase = passwordRecoveryUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}