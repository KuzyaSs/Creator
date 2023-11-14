package ru.ermakov.creator.presentation.screen.passwordRecovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.passwordRecovery.PasswordRecoveryUseCase
import ru.ermakov.creator.presentation.exception.ExceptionHandler

class PasswordRecoveryViewModelFactory(
    private val passwordRecoveryUseCase: PasswordRecoveryUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordRecoveryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PasswordRecoveryViewModel(
                passwordRecoveryUseCase = passwordRecoveryUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}