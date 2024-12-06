package org.sopt.and.data.datasourceImpl

import org.sopt.and.data.datasource.UserRemoteDataSource
import org.sopt.and.data.dto.request.RequestLoginDto
import org.sopt.and.data.dto.request.RequestSignUpDto
import org.sopt.and.data.dto.response.ResponseDto
import org.sopt.and.data.dto.response.ResponseHobbyDto
import org.sopt.and.data.service.UserService
import retrofit2.Response

class UserRemoteDataSourceImpl(
    private val userService: UserService
) : UserRemoteDataSource {
    override suspend fun signUp(
        username: String,
        password: String,
        hobby: String
    ): Response<ResponseDto> =
        userService.postSignUp(RequestSignUpDto(username, password, hobby))

    override suspend fun login(
        username: String,
        password: String
    ): Response<ResponseDto> =
        userService.postLogin(RequestLoginDto(username, password))

    override suspend fun getHobby(token: String): Response<ResponseHobbyDto> =
        userService.getHobby(token)
}