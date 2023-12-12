package ru.ermakov.creator.presentation.screen.subscriptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SubscriptionsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubscriptionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SubscriptionsViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}