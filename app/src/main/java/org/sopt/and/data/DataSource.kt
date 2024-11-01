package org.sopt.and.data

import android.content.SharedPreferences

class DataSource(private val sharedPreferences: SharedPreferences) {

    fun saveUserInfo(email: String, password: String) {
        sharedPreferences.edit().apply {
            putString(KEY_USER_EMAIL, email)
            putString(KEY_USER_PASSWORD, password)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    fun getEmail(): String? {
        return sharedPreferences.getString(KEY_USER_EMAIL, null)
    }

    fun getPassword(): String? {
        return sharedPreferences.getString(KEY_USER_PASSWORD, null)
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun clearUserCredentials() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val KEY_USER_EMAIL = "USER_EMAIL"
        private const val KEY_USER_PASSWORD = "USER_PASSWORD"
        private const val KEY_IS_LOGGED_IN = "IS_LOGGED_IN"
    }

}