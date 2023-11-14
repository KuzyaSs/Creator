package ru.ermakov.creator.data.local.dataSource

import ru.ermakov.creator.domain.model.SignInData

interface AuthLocalDataSource {
    fun saveSignInData(signInData: SignInData)
    fun getSignInData(): SignInData
    fun clearSignInData()
}