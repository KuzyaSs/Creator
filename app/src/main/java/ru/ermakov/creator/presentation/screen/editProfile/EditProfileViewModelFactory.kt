package ru.ermakov.creator.presentation.screen.editProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.account.GetCurrentUserUseCase
import ru.ermakov.creator.presentation.exception.ExceptionHandler

class EditProfileViewModelFactory(
    private val exceptionHandler: ExceptionHandler,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditProfileViewModel(
                exceptionHandler = exceptionHandler,
                getCurrentUserUseCase = getCurrentUserUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}