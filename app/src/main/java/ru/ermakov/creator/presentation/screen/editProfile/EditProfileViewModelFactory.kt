package ru.ermakov.creator.presentation.screen.editProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.common.CancelUploadTaskUseCase
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateBioUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateUserImageUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UpdateUsernameUseCase
import ru.ermakov.creator.domain.useCase.editProfile.UploadProfileFileUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

class EditProfileViewModelFactory(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateUserImageUseCase: UpdateUserImageUseCase,
    private val updateUsernameUseCase: UpdateUsernameUseCase,
    private val updateBioUseCase: UpdateBioUseCase,
    private val uploadProfileFileUseCase: UploadProfileFileUseCase,
    private val cancelUploadTaskUseCase: CancelUploadTaskUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditProfileViewModel(
                getCurrentUserUseCase = getCurrentUserUseCase,
                updateUserImageUseCase = updateUserImageUseCase,
                updateUsernameUseCase = updateUsernameUseCase,
                updateBioUseCase = updateBioUseCase,
                uploadProfileFileUseCase = uploadProfileFileUseCase,
                cancelUploadTaskUseCase = cancelUploadTaskUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}