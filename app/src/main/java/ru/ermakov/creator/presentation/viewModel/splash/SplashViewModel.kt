package ru.ermakov.creator.presentation.viewModel.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.domain.useCase.auth.signIn.SignedInUseCase
import ru.ermakov.creator.util.Resource

class SplashViewModel(private val signedInUseCase: SignedInUseCase) : ViewModel() {
    private val _signInData = MutableLiveData<Resource<SignInData>>()
    val signInData: LiveData<Resource<SignInData>> = _signInData

    fun checkSignedInStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            _signInData.postValue(signedInUseCase.execute())
        }
    }
}