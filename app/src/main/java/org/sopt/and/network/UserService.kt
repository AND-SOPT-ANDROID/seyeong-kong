package org.sopt.and.network

import org.sopt.and.network.request.RequestLoginDto
import org.sopt.and.network.request.RequestSignUpDto
import org.sopt.and.network.response.ResponseDto
import org.sopt.and.network.response.ResponseHobbyDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @POST("/user")
    suspend fun postSignUp(
        @Body request: RequestSignUpDto
    ): Response<ResponseDto>

    @POST("/login")
    suspend fun postLogin(
        @Body request: RequestLoginDto
    ): Response<ResponseDto>

    @GET("/user/my-hobby")
    suspend fun getHobby(
        @Header("token") token: String
    ): Response<ResponseHobbyDto>
}
