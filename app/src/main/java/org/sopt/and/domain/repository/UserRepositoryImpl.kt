package org.sopt.and.domain.repository

import org.sopt.and.data.datasource.UserDataLocalSource
import org.sopt.and.data.datasource.UserRemoteDataSource
import org.sopt.and.data.repository.UserRepository
import org.sopt.and.domain.entity.UserEntity

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserDataLocalSource,
) : UserRepository {

    override suspend fun signUp(
        username: String,
        password: String,
        hobby: String
    ): Result<UserEntity> =
        runCatching {
            val response = userRemoteDataSource.signUp(username, password, hobby)

            if (response.isSuccessful) {
                response.body()?.let { responseDto ->
                    val userEntity = UserEntity(
                        username = username,
                        password = password,
                        hobby = hobby,
                        token = responseDto.result?.token
                    )
                    saveUserInfo(userEntity)
                    userEntity
                } ?: throw Exception("Response body is null")
            } else {
                throw Exception("Sign up failed: ${response.message()}")
            }
        }

    override suspend fun login(
        username: String,
        password: String
    ): Result<String> =
        runCatching {
            val response = userRemoteDataSource.login(username, password)

            if (response.isSuccessful) {
                response.body()?.result?.token?.also { token ->
                    saveUserToken(token)
                } ?: throw Exception("Token is null")
            } else {
                throw Exception("Login failed: ${response.message()}")
            }
        }

    override suspend fun getHobby(token: String): Result<String> =
        runCatching {
            val response = userRemoteDataSource.getHobby(token)

            if (response.isSuccessful) {
                response.body()?.result?.hobby ?: throw Exception("Hobby is null")
            } else {
                throw Exception("Failed to get hobby: ${response.message()}")
            }
        }

    override fun saveUserInfo(userEntity: UserEntity) {
        with(userEntity) {
            userLocalDataSource.saveUserInfo(username, password, hobby)
        }
    }

    override fun saveUserToken(token: String) {
        userLocalDataSource.saveUserToken(token)
    }

    override fun getUserInfo(): Result<UserEntity> =
        runCatching {
            val username = userLocalDataSource.getUsername()
            val password = userLocalDataSource.getPassword()
            val hobby = userLocalDataSource.getHobby()
            val token = userLocalDataSource.getToken()

            if (username != null && password != null && hobby != null) {
                UserEntity(
                    username = username,
                    password = password,
                    hobby = hobby,
                    token = token
                )
            } else {
                throw Exception("User info not found")
            }
        }

    override fun getToken(): String? = userLocalDataSource.getToken()

    override fun isLoggedIn(): Boolean = userLocalDataSource.isLoggedIn()

    override fun logout() {
        userLocalDataSource.clearUserCredentials()
    }
}
