package ru.ermakov.creator.data.local.dataSource

import android.content.SharedPreferences
import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.util.Constant.Companion.EMPTY_STRING
import ru.ermakov.creator.util.Constant.Companion.KEY_EMAIL
import ru.ermakov.creator.util.Constant.Companion.KEY_PASSWORD

class AuthLocalDataSourceImpl(
    private val sharedPreferences: SharedPreferences
) : AuthLocalDataSource {
    override fun save(signInData: SignInData) {
        sharedPreferences.edit().apply {
            putString(KEY_EMAIL, signInData.email)
            putString(KEY_PASSWORD, signInData.password)
        }.apply()
    }

    override fun get(): SignInData {
        sharedPreferences.apply {
            return SignInData(
                email = getString(KEY_EMAIL, EMPTY_STRING) ?: EMPTY_STRING,
                password = getString(KEY_PASSWORD, EMPTY_STRING) ?: EMPTY_STRING
            )
        }
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}