package ru.ermakov.creator.domain.useCase.editProfile

import ru.ermakov.creator.domain.useCase.common.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.common.UpdateUserUseCase

class UpdateBioUseCase(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) {
    suspend operator fun invoke(bio: String) {
        val updatedUser = getCurrentUserUseCase().copy(bio = bio)
        updateUserUseCase(user = updatedUser)
    }
}