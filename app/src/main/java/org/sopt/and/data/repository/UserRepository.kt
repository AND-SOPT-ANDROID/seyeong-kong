package org.sopt.and.data.repository

import org.sopt.and.domain.entity.UserEntity

interface UserRepository {
    suspend fun signUp(username: String, password: String, hobby: String): Result<UserEntity>
    suspend fun login(username: String, password: String): Result<String>
    suspend fun getHobby(token: String): Result<String>
    fun saveUserInfo(userEntity: UserEntity)
    fun saveUserToken(token: String)
    fun getUserInfo(): Result<UserEntity>
    fun getToken(): String?
    fun isLoggedIn(): Boolean
    fun logout()
}
