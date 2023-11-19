package ru.ermakov.creator.domain.useCase.common

import kotlinx.coroutines.flow.Flow
import ru.ermakov.creator.domain.model.UploadedFile
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.domain.repository.FileRepository

private const val PROFILE_AVATAR_PATH = "/Profile avatar/"

class UploadFileUseCase(private val fileRepository: FileRepository) {
    suspend operator fun invoke(user: User, uri: String, name: String): Flow<UploadedFile> {
        return fileRepository.uploadFile(
            uri = uri,
            path = user.id + PROFILE_AVATAR_PATH + name
        )
    }
}