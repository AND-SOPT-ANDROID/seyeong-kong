package org.sopt.and.data.datasource

interface UserDataLocalSource {
    fun saveUserInfo(username: String, password: String, hobby: String)
    fun saveUserToken(token: String)
    fun getToken(): String?
    fun getUsername(): String?
    fun getPassword(): String?
    fun getHobby(): String?
    fun isLoggedIn(): Boolean
    fun clearUserCredentials()
}