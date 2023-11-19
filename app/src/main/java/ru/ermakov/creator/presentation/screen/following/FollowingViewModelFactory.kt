package ru.ermakov.creator.presentation.screen.following

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.account.SignOutUseCase
import ru.ermakov.creator.presentation.exception.ExceptionHandler

class FollowingViewModelFactory(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FollowingViewModel(
                getCurrentUserUseCase = getCurrentUserUseCase,
                signOutUseCase = signOutUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}