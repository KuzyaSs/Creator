package ru.ermakov.creator.presentation.screen.createCreditGoal

data class CreateCreditGoalUiState(
    val isCreditGoalCreated: Boolean = false,
    val isProgressBarCreateShown: Boolean = false,
    val isErrorMessageShown: Boolean = false,
    val errorMessage: String = ""
)