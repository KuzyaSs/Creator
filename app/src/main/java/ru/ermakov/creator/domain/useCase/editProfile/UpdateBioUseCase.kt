package ru.ermakov.creator.domain.useCase.editProfile

import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.shared.UpdateUserUseCase

class UpdateBioUseCase(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) {
    suspend operator fun invoke(bio: String) {
        val updatedUser = getCurrentUserUseCase().copy(bio = bio)
        updateUserUseCase(user = updatedUser)
    }
}