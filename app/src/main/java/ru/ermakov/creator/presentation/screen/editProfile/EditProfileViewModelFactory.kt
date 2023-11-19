package ru.ermakov.creator.presentation.screen.editProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.useCase.common.CancelUploadTaskUseCase
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.common.UpdateUserUseCase
import ru.ermakov.creator.domain.useCase.common.UploadFileUseCase
import ru.ermakov.creator.presentation.exception.ExceptionHandler

class EditProfileViewModelFactory(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val cancelUploadTaskUseCase: CancelUploadTaskUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditProfileViewModel(
                getCurrentUserUseCase = getCurrentUserUseCase,
                updateUserUseCase = updateUserUseCase,
                uploadFileUseCase = uploadFileUseCase,
                cancelUploadTaskUseCase = cancelUploadTaskUseCase,
                exceptionHandler = exceptionHandler
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}