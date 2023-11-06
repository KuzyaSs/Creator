package ru.ermakov.creator.presentation.screen.passwordRecovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.useCase.passwordRecovery.PasswordRecoveryUseCase
import ru.ermakov.creator.util.Resource

class PasswordRecoveryViewModel(private val passwordRecoveryUseCase: PasswordRecoveryUseCase) :
    ViewModel() {
    private val _email = MutableLiveData<Resource<String>>()
    val email: LiveData<Resource<String>> = _email

    fun resetPassword(email: String) {
        _email.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _email.postValue(passwordRecoveryUseCase.execute(email = email))
        }
    }
}