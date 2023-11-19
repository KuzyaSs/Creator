package ru.ermakov.creator.presentation.screen.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.domain.useCase.common.GetCurrentUserUseCase
import ru.ermakov.creator.domain.useCase.account.SignOutUseCase
import ru.ermakov.creator.presentation.State
import ru.ermakov.creator.presentation.exception.ExceptionHandler
import java.time.LocalDate

class FollowingViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _followingUiState = MutableLiveData(FollowingUiState())
    val followingUiState: LiveData<FollowingUiState> get() = _followingUiState

    init {
        setCurrentUser()
    }

    fun setCurrentUser() {
        _followingUiState.value = _followingUiState.value?.copy(state = State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(2000) // remove
                // temporarily
                val currentUser = User(
                    "12",
                    "Kuzya",
                    "skepy@,ail.ru",
                    "about",
                    "https://firebasestorage.googleapis.com/v0/b/creator-26c44.appspot.com/o/myAvatar.png?alt=media&token=e564220f-2ed0-4016-92fd-52f1253072f9",
                    "-",
                    LocalDate.now()
                )   // getCurrentUserUseCase()
                _followingUiState.postValue(
                    _followingUiState.value?.copy(
                        currentUser = currentUser,
                        state = State.SUCCESS
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                _followingUiState.postValue(
                    _followingUiState.value?.copy(
                        errorMessage = errorMessage,
                        state = State.ERROR
                    )
                )
            }
        }
    }

    fun signOut() {
        signOutUseCase()
    }
}