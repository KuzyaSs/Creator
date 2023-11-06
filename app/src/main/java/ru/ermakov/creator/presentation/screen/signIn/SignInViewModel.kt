package ru.ermakov.creator.presentation.screen.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.domain.useCase.signIn.SignInUseCase
import ru.ermakov.creator.util.Resource

class SignInViewModel(private val signInUseCase: SignInUseCase) : ViewModel() {
    private val _signInData = MutableLiveData<Resource<SignInData>>()
    val signInData: LiveData<Resource<SignInData>> get() = _signInData

    fun signIn(signInData: SignInData) {
        _signInData.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _signInData.postValue(signInUseCase.execute(signInData = signInData))
        }
    }
}