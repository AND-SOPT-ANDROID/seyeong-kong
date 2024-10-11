package org.sopt.and.data

import android.content.SharedPreferences

class DataSource(private val sharedPreferences: SharedPreferences) {

    fun saveUserInfo(email: String, password: String) {
        sharedPreferences.edit().apply {
            putString("USER_EMAIL", email)
            putString("USER_PASSWORD", password)
            putBoolean("IS_LOGGED_IN", true)
            apply()
        }
    }

    fun getEmail(): String? {
        return sharedPreferences.getString("USER_EMAIL", null)
    }

    fun getPassword(): String? {
        return sharedPreferences.getString("USER_PASSWORD", null)
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("IS_LOGGED_IN", false)
    }

    fun clearUserCredentials() {
        sharedPreferences.edit().clear().apply()
    }
}
