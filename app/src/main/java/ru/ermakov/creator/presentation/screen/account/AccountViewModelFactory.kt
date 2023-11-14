package ru.ermakov.creator.presentation.screen.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.repository.UserRepository
import ru.ermakov.creator.domain.useCase.account.GetCurrentUserUseCase
import ru.ermakov.creator.presentation.exception.ExceptionHandler

class AccountViewModelFactory(
    private val exceptionHandler: ExceptionHandler,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountViewModel(
                exceptionHandler = exceptionHandler,
                getCurrentUserUseCase = getCurrentUserUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}