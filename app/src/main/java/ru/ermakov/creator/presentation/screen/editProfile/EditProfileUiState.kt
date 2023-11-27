package ru.ermakov.creator.presentation.screen.editProfile

import ru.ermakov.creator.domain.model.EditProfileImageOption
import ru.ermakov.creator.domain.model.User

data class EditProfileUiState(
    val currentUser: User? = null,
    val selectedEditProfileImageOption: EditProfileImageOption = EditProfileImageOption.PROFILE_AVATAR,
    val isRefreshingShown: Boolean = false,
    val isLoadingShown: Boolean = false,
    val isEditProfileErrorMessageShown: Boolean = false,
    val editProfileErrorMessage: String = "",
    val isEditUsernameErrorMessageShown: Boolean = false,
    val editUsernameErrorMessage: String = "",
    val isEditBioErrorMessageShown: Boolean = false,
    val editBioErrorMessage: String = ""
)