package ru.ermakov.creator.presentation.screen.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.data.repository.user.UserRepository
import ru.ermakov.creator.util.Resource

class AccountViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>> = _user

    fun setUser() {
        _user.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getCurrentUser().collect { userResource ->
                _user.postValue(userResource)
            }
        }
    }

    fun detachUserListeners() {
        userRepository.detachUserListener()
    }
}