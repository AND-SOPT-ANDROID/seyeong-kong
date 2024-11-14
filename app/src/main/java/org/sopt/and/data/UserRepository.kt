package org.sopt.and.data

import android.content.Context
import org.sopt.and.network.UserService
import org.sopt.and.network.request.RequestLoginDto
import org.sopt.and.network.request.RequestSignUpDto
import org.sopt.and.network.response.ResponseDto
import org.sopt.and.network.response.ResponseHobbyDto
import retrofit2.Response

class UserRepository(
    private val userService: UserService,
    context: Context) {
    private val dataSource = DataSource(context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE))

    suspend fun postSignUp(username: String, password: String, hobby: String): Response<ResponseDto> {
        val request = RequestSignUpDto(username, password, hobby)
        return userService.postSignUp(request)
    }

    suspend fun postLogin(username: String, password: String): Response<ResponseDto> {
        val request = RequestLoginDto(username, password)
        return userService.postLogin(request)
    }

    suspend fun getHobby(token: String): Response<ResponseHobbyDto> {
        return userService.getHobby(token)
    }

    fun saveUserInfo(username: String, password: String, hobby: String) {
        dataSource.saveUserInfo(username, password, hobby)
    }

    fun saveUserToken(token: String) {
        dataSource.saveUserToken(token)
    }

    fun getUsername(): String? = dataSource.getUsername()
    fun getPassword(): String? = dataSource.getPassword()
    fun getHobby(): String? = dataSource.getHobby()
    fun getToken(): String? = dataSource.getToken()

    fun isLoggedIn(): Boolean {
        return dataSource.isLoggedIn()
    }

    fun logout() {
        dataSource.clearUserCredentials()
    }

}
