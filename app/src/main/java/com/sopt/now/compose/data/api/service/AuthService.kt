package com.sopt.now.compose.data.api.service

import com.sopt.now.compose.data.api.ApiKeyStorage.INFO
import com.sopt.now.compose.data.api.ApiKeyStorage.JOIN
import com.sopt.now.compose.data.api.ApiKeyStorage.LOGIN
import com.sopt.now.compose.data.api.ApiKeyStorage.MEMBER
import com.sopt.now.compose.data.request.RequestLoginDto
import com.sopt.now.compose.data.request.RequestSignUpDto
import com.sopt.now.compose.data.response.BaseResponse
import com.sopt.now.data.dto.remote.response.ResponseMemberInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("$MEMBER/$JOIN")
    suspend fun signUp(
        @Body request: RequestSignUpDto
    ): Response<BaseResponse<Unit>>

    @POST("$MEMBER/$LOGIN")
    suspend fun login(
        @Body request: RequestLoginDto
    ): Response<BaseResponse<Unit>>

    @GET("$MEMBER/$INFO")
    suspend fun getMemberInfo(
        @Header("memberId") id: Int
    ): Response<BaseResponse<ResponseMemberInfo>>
}
