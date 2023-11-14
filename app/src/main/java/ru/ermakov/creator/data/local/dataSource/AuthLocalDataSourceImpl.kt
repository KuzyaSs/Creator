package ru.ermakov.creator.data.local.dataSource

import android.content.SharedPreferences
import ru.ermakov.creator.domain.model.SignInData

private const val KEY_EMAIL = "email"
private const val KEY_PASSWORD = "password"

class AuthLocalDataSourceImpl(
    private val sharedPreferences: SharedPreferences
) : AuthLocalDataSource {
    override fun saveSignInData(signInData: SignInData) {
        sharedPreferences.edit().apply {
            putString(KEY_EMAIL, signInData.email)
            putString(KEY_PASSWORD, signInData.password)
        }.apply()
    }

    override fun getSignInData(): SignInData {
        sharedPreferences.apply {
            return SignInData(
                email = getString(KEY_EMAIL, "").orEmpty(),
                password = getString(KEY_PASSWORD, "").orEmpty()
            )
        }
    }

    override fun clearSignInData() {
        sharedPreferences.edit().clear().apply()
    }
}