package org.sopt.and.data

import android.content.Context

class UserRepository(context: Context) {
    private val dataSource = DataSource(context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE))

    fun saveUserInfo(email: String, password: String) {
        dataSource.saveUserInfo(email, password)
    }

    fun getEmail(): String? {
        return dataSource.getEmail()
    }

    fun getPassword(): String? {
        return dataSource.getPassword()
    }

    fun isLoggedIn(): Boolean {
        return dataSource.isLoggedIn()
    }

    fun logout() {
        dataSource.clearUserCredentials()
    }
}
