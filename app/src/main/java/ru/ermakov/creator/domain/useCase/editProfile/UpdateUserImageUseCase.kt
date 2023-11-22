package ru.ermakov.creator.domain.useCase.editProfile

import ru.ermakov.creator.domain.model.EditProfileImageOption
import ru.ermakov.creator.domain.model.UploadedFile
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.common.UpdateUserUseCase

class UpdateUserImageUseCase(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) {
    suspend operator fun invoke(
        uploadedFile: UploadedFile,
        editProfileImageOption: EditProfileImageOption?
    ) {
        if (uploadedFile.url.isEmpty()) {
            return
        }

        val currentUser = getCurrentUserUseCase()
        when (editProfileImageOption) {
            EditProfileImageOption.PROFILE_AVATAR -> {
                val updatedUser = currentUser.copy(profileAvatarUrl = uploadedFile.url)
                updateUserUseCase(user = updatedUser)
            }

            EditProfileImageOption.PROFILE_BACKGROUND -> {
                val updatedUser = currentUser.copy(profileBackgroundUrl = uploadedFile.url)
                updateUserUseCase(user = updatedUser)
            }

            else -> {}
        }
    }
}