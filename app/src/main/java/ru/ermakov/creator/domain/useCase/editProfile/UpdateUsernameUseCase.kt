package ru.ermakov.creator.domain.useCase.editProfile

import ru.ermakov.creator.domain.exception.ShortUsernameException
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.common.UpdateUserUseCase

private const val MINIMUM_USERNAME_LENGTH = 3

class UpdateUsernameUseCase(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) {
    suspend operator fun invoke(username: String) {
        if (username.length < MINIMUM_USERNAME_LENGTH) {
            throw ShortUsernameException()
        }

        val updatedUser = getCurrentUserUseCase().copy(username = username)
        updateUserUseCase(user = updatedUser)
    }
}