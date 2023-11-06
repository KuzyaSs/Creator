package ru.ermakov.creator.presentation.screen.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.domain.useCase.signUp.SignUpUseCase
import ru.ermakov.creator.util.Resource

class SignUpViewModel(private val signUpUseCase: SignUpUseCase) : ViewModel() {
    private val _signUpData = MutableLiveData<Resource<SignUpData>>()
    val signUpData: LiveData<Resource<SignUpData>> get() = _signUpData

    private var _isNavigationAvailable = false
    val isNavigationAvailable get() = _isNavigationAvailable

    fun signUp(signUpData: SignUpData) {
        _isNavigationAvailable = true
        _signUpData.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _signUpData.postValue(signUpUseCase.execute(signUpData = signUpData))
        }
    }

    fun resetIsNavigationAvailable() {
        _isNavigationAvailable = false
    }
}