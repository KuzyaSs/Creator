package ru.ermakov.creator.domain.useCase.editProfile

import ru.ermakov.creator.data.remote.dataSource.FileRemoteDataSource
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.domain.repository.UserRepository

private const val PROFILE_AVATAR_PATH = "/Profile avatar/"

class EditProfileAvatarUseCase(
    private val fileRemoteDataSource: FileRemoteDataSource,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User, uri: String, name: String) {
        val profileAvatarUrl = fileRemoteDataSource.uploadFile(
            uri = uri,
            path = user.id + PROFILE_AVATAR_PATH + name
        )
        userRepository.updateUser(user.copy(profileAvatarUrl = profileAvatarUrl))
    }
}