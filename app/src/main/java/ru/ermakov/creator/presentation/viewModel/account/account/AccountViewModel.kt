package ru.ermakov.creator.presentation.viewModel.account.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.util.Resource

class AccountViewModel : ViewModel() {
    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>> = _user

    fun setUser() {
        _user.postValue(Resource.Loading())
        // _user.postValue(getUserUseCase.getUser())
    }
}