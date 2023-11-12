package ru.ermakov.creator.data.local.dataSource

import ru.ermakov.creator.domain.model.SignInData

interface AuthLocalDataSource {
    fun save(signInData: SignInData)
    fun get(): SignInData
    fun clear()
}