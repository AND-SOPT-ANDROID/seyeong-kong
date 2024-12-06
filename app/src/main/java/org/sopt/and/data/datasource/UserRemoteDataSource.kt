package org.sopt.and.data.datasource

import org.sopt.and.data.dto.response.ResponseDto
import org.sopt.and.data.dto.response.ResponseHobbyDto
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun signUp(username: String, password: String, hobby: String): Response<ResponseDto>
    suspend fun login(username: String, password: String): Response<ResponseDto>
    suspend fun getHobby(token: String): Response<ResponseHobbyDto>
}