package ru.ermakov.creator.domain.useCase.editProfile

import kotlinx.coroutines.flow.Flow
import ru.ermakov.creator.domain.exception.UnexpectedValueException
import ru.ermakov.creator.domain.model.EditProfileImageOption
import ru.ermakov.creator.domain.model.UploadedFile
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.domain.repository.FileRepository

private const val PROFILE_AVATAR_PATH = "/Profile avatar/"
private const val PROFILE_BACKGROUND_PATH = "/Profile background/"

class UploadProfileFileUseCase(private val fileRepository: FileRepository) {
    suspend operator fun invoke(
        user: User?,
        uri: String,
        fileName: String,
        editProfileImageOption: EditProfileImageOption?
    ): Flow<UploadedFile> {
        if (user == null) {
            throw UnexpectedValueException()
        }

        return when (editProfileImageOption) {
            EditProfileImageOption.PROFILE_AVATAR -> {
                fileRepository.uploadFile(
                    uri = uri,
                    path = user.id + PROFILE_AVATAR_PATH + fileName
                )
            }

            EditProfileImageOption.PROFILE_BACKGROUND -> {
                fileRepository.uploadFile(
                    uri = uri,
                    path = user.id + PROFILE_BACKGROUND_PATH + fileName
                )
            }

            else -> {
                throw UnexpectedValueException()
            }
        }
    }
}