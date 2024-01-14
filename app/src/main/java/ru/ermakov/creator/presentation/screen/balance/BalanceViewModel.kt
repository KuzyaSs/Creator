package ru.ermakov.creator.presentation.screen.balance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.ermakov.creator.domain.model.UserTransactionItem
import ru.ermakov.creator.domain.useCase.balance.SearchUserTransactionPageByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetBalanceByUserIdUseCase
import ru.ermakov.creator.domain.useCase.shared.GetCurrentUserIdUseCase
import ru.ermakov.creator.presentation.util.ExceptionHandler

private const val SEARCH_USER_TRANSACTIONS_DELAY = 1000L

class BalanceViewModel(
    private val searchUserTransactionPageByUserIdUseCase: SearchUserTransactionPageByUserIdUseCase,
    private val getBalanceByUserIdUseCase: GetBalanceByUserIdUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val exceptionHandler: ExceptionHandler
) : ViewModel() {
    private val _balanceUiState = MutableLiveData(BalanceUiState())
    val balanceUiState: LiveData<BalanceUiState> = _balanceUiState

    private var searchFollowsJob: Job? = null
    private var loadNextFollowPageJob: Job? = null

    init {
        searchUserTransactions()
    }

    fun refreshUserTransactions() {
        _balanceUiState.value = _balanceUiState.value?.copy(isRefreshingShown = true)
        searchUserTransactions()
    }

    fun loadNextUserTransactionPage() {
        if (_balanceUiState.value?.isLoadingShown == true) {
            return
        }
        loadNextFollowPageJob?.cancel()

        _balanceUiState.value = _balanceUiState.value?.copy(
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        loadNextFollowPageJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(SEARCH_USER_TRANSACTIONS_DELAY)
                var nextUserTransactionPage =
                    (_balanceUiState.value?.currentUserTransactionPage ?: 0) + 1
                val currentUserTransactionItems =
                    _balanceUiState.value?.userTransactionItems ?: listOf()
                val followingUserTransactionItems: List<UserTransactionItem> =
                    searchUserTransactionPageByUserIdUseCase(
                        userId = getCurrentUserIdUseCase(),
                        page = nextUserTransactionPage
                    )
                if (followingUserTransactionItems.isEmpty()) {
                    nextUserTransactionPage--
                }
                _balanceUiState.postValue(
                    _balanceUiState.value?.copy(
                        userTransactionItems = currentUserTransactionItems + followingUserTransactionItems,
                        currentUserTransactionPage = nextUserTransactionPage,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                if (errorMessage.isNotBlank()) {
                    _balanceUiState.postValue(
                        _balanceUiState.value?.copy(
                            userTransactionItems = _balanceUiState.value?.userTransactionItems,
                            isRefreshingShown = false,
                            isLoadingShown = false,
                            isErrorMessageShown = true,
                            errorMessage = errorMessage
                        )
                    )
                }
            }
        }
    }

    fun clearErrorMessage() {
        _balanceUiState.value = _balanceUiState.value?.copy(
            isErrorMessageShown = false,
            errorMessage = ""
        )
    }

    private fun searchUserTransactions() {
        searchFollowsJob?.cancel()
        loadNextFollowPageJob?.cancel()

        _balanceUiState.value = _balanceUiState.value?.copy(
            isLoadingShown = true,
            isErrorMessageShown = false
        )
        searchFollowsJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(SEARCH_USER_TRANSACTIONS_DELAY)
                val userTransactionItems: List<UserTransactionItem> =
                    searchUserTransactionPageByUserIdUseCase(
                        userId = getCurrentUserIdUseCase(),
                        page = 0
                    )
                _balanceUiState.postValue(
                    _balanceUiState.value?.copy(
                        balance = getBalanceByUserIdUseCase(userId = getCurrentUserIdUseCase()),
                        userTransactionItems = userTransactionItems,
                        currentUserTransactionPage = 0,
                        isRefreshingShown = false,
                        isLoadingShown = false,
                        isErrorMessageShown = false
                    )
                )
            } catch (exception: Exception) {
                val errorMessage = exceptionHandler.handleException(exception = exception)
                if (errorMessage.isNotBlank()) {
                    _balanceUiState.postValue(
                        _balanceUiState.value?.copy(
                            userTransactionItems = _balanceUiState.value?.userTransactionItems,
                            isRefreshingShown = false,
                            isLoadingShown = false,
                            isErrorMessageShown = true,
                            errorMessage = errorMessage
                        )
                    )
                }
            }
        }
    }
}