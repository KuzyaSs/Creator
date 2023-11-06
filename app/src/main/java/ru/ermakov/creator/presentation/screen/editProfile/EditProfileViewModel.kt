package ru.ermakov.creator.presentation.screen.editProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.util.Resource

class EditProfileViewModel : ViewModel() {
    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>> = _user

    fun setUser() {
        _user.postValue(Resource.Loading())
        // _user.postValue(getUserUseCase.execute())
    }

    fun editUsername(user: User) {
        // editProfileUseCase.execute(user)
    }
}