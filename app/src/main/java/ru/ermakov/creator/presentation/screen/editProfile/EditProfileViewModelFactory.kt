package ru.ermakov.creator.presentation.screen.editProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.account.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.editProfile.EditProfileAvatarUseCase
import ru.ermakov.creator.presentation.exception.ExceptionHandler

class EditProfileViewModelFactory(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val editProfileAvatarUseCase: EditProfileAvatarUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditProfileViewModel(
                getCurrentUserUseCase = getCurrentUserUseCase,
                editProfileAvatarUseCase = editProfileAvatarUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}