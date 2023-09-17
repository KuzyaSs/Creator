package ru.ermakov.creator.presentation.viewModel.account.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.domain.repository.UserRepository

class AccountViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccountViewModel(userRepository = userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}