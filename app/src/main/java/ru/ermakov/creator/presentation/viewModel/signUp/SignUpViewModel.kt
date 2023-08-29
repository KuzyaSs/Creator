package ru.ermakov.creator.presentation.viewModel.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.domain.useCase.signUp.SignUpUseCase
import ru.ermakov.creator.util.Resource

class SignUpViewModel(private val signUpUseCase: SignUpUseCase) : ViewModel() {
    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>> = _user

    private var _isNavigationAvailable = false
    val isNavigationAvailable get() = _isNavigationAvailable

    fun signUp(signUpData: SignUpData) {
        _isNavigationAvailable = true
        _user.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _user.postValue(signUpUseCase.execute(signUpData = signUpData))
        }
    }

    fun resetIsNavigationAvailable() {
        _isNavigationAvailable = false
    }
}