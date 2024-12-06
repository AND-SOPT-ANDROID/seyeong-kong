package org.sopt.and.data.datasourceImpl

import android.content.SharedPreferences
import org.sopt.and.data.datasource.UserDataLocalSource

class UserDataLocalSourceImpl(
    private val sharedPreferences: SharedPreferences
) : UserDataLocalSource {

    override fun saveUserInfo(username: String, password: String, hobby: String) {
        sharedPreferences.edit().apply {
            putString(KEY_USER_USERNAME, username)
            putString(KEY_USER_PASSWORD, password)
            putString(KEY_USER_HOBBY, hobby)
            apply()
        }
    }

    override fun saveUserToken(token: String) {
        sharedPreferences.edit().apply {
            putString(KEY_USER_TOKEN, token)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(KEY_USER_TOKEN, null)
    }

    override fun getUsername(): String? {
        return sharedPreferences.getString(KEY_USER_USERNAME, null)
    }

    override fun getPassword(): String? {
        return sharedPreferences.getString(KEY_USER_PASSWORD, null)
    }

    override fun getHobby(): String? {
        return sharedPreferences.getString(KEY_USER_HOBBY, null)
    }

    override fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    override fun clearUserCredentials() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val KEY_USER_USERNAME = "USER_USERNAME"
        private const val KEY_USER_PASSWORD = "USER_PASSWORD"
        private const val KEY_USER_HOBBY = "USER_HOBBY"
        private const val KEY_USER_TOKEN = "USER_TOKEN"
        private const val KEY_IS_LOGGED_IN = "IS_LOGGED_IN"
    }
}