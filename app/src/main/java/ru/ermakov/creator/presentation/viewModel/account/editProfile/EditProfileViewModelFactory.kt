package ru.ermakov.creator.presentation.viewModel.account.editProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditProfileViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditProfileViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}